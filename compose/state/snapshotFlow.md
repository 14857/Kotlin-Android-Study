# snapshotFlow

## 목차

1. snapshotFlow란?
2. 왜 사용하는가?
3. snapshotFlow 없이 구현하기
4. snapshotFlow 사용하기
5. 실전 예제 - 스크롤 위치 감지
6. 실전 예제 - 검색어 변경 감지
7. debounce와 함께 사용하기
8. LaunchedEffect와 함께 사용하는 이유
9. derivedStateOf와 차이점
10. snapshotFlow 사용 시 주의사항
11. 언제 사용하면 좋을까?
12. 정리

<br>

## 1. snapshotFlow란?

snapshotFlow는 Compose State를 Kotlin Flow로 변환해주는 함수이다.

Compose에서는 State가 변경되면 자동으로 Recomposition이 발생한다.

하지만 실제 앱에서는 단순히 UI를 다시 그리는 것 외에도

- API 호출
- 로그 전송
- 사용자 행동 분석
- 페이지네이션
- 스크롤 추적

같은 작업이 필요하다.

이때 Compose State의 변화를 Flow 형태로 감지할 수 있도록 만들어주는 것이 snapshotFlow이다.

```kotlin
snapshotFlow {
    state.value
}
```

State 값이 변경될 때마다 새로운 값이 Flow로 전달된다.

<br>

## 2. 왜 사용하는가?

Compose State는 UI 상태를 관리하기 위한 도구이다.

반면 Flow는 비동기 데이터 처리와 이벤트 처리를 위한 도구이다.

실제 앱에서는 두 개를 연결해야 하는 경우가 많다.

예를 들어

```text
검색어 입력
↓
API 호출

스크롤 이동
↓
페이지네이션 요청

버튼 클릭
↓
로그 전송
```

이러한 이벤트 기반 작업을 처리하기 위해 snapshotFlow를 사용한다.

<br>

## 3. snapshotFlow 없이 구현하기

검색어가 변경될 때마다 로그를 출력한다고 가정해보자.

```kotlin
var keyword by remember {
    mutableStateOf("")
}

TextField(
    value = keyword,
    onValueChange = {
        keyword = it

        println("검색어 변경 : $it")
    }
)
```

동작은 하지만 문제가 있다.

- Flow 연산자 사용 불가
- debounce 사용 불가
- collect 사용 불가
- 비동기 처리 어려움

실무에서는 유지보수가 어려워진다.

<br>

## 4. snapshotFlow 사용하기

```kotlin
var keyword by remember {
    mutableStateOf("")
}

LaunchedEffect(Unit) {

    snapshotFlow {
        keyword
    }.collect { value ->

        println("검색어 변경 : $value")
    }
}
```

동작 순서

```text
keyword 변경
↓
snapshotFlow 감지
↓
새로운 값 방출
↓
collect 수신
↓
원하는 작업 실행
```

Compose State를 Flow처럼 사용할 수 있게 된다.

<br>

## 5. 실전 예제 - 스크롤 위치 감지

snapshotFlow의 대표적인 사용 사례이다.

```kotlin
@Composable
fun ScrollScreen() {

    val listState = rememberLazyListState()

    LaunchedEffect(listState) {

        snapshotFlow {
            listState.firstVisibleItemIndex
        }.collect { index ->

            println("현재 위치 : $index")
        }
    }

    LazyColumn(
        state = listState
    ) {

        items(100) {

            Text(
                text = "Item $it"
            )
        }
    }
}
```

실행 결과

```text
현재 위치 : 0
현재 위치 : 1
현재 위치 : 2
현재 위치 : 3
```

스크롤이 이동할 때마다 현재 위치를 확인할 수 있다.

<br>

## 6. 실전 예제 - 검색어 변경 감지

검색 기능에서 자주 사용하는 패턴이다.

```kotlin
var keyword by remember {
    mutableStateOf("")
}

LaunchedEffect(Unit) {

    snapshotFlow {
        keyword
    }.collect { query ->

        println("검색 요청 : $query")
    }
}
```

사용자가 입력할 때마다 값을 수신할 수 있다.

<br>

## 7. debounce와 함께 사용하기

실무에서는 입력할 때마다 API를 호출하지 않는다.

사용자의 입력이 잠시 멈췄을 때 호출한다.

```kotlin
LaunchedEffect(Unit) {

    snapshotFlow {
        keyword
    }
        .debounce(500)
        .collect { query ->

            search(query)
        }
}
```

예를 들어

```text
a 입력
ab 입력
abc 입력

500ms 대기

검색 실행 : abc
```

불필요한 네트워크 요청을 줄일 수 있다.

<br>

## 8. LaunchedEffect와 함께 사용하는 이유

snapshotFlow는 Coroutine 안에서 실행되어야 한다.

그래서 대부분 LaunchedEffect 안에서 사용한다.

```kotlin
LaunchedEffect(Unit) {

    snapshotFlow {
        state
    }.collect {

    }
}
```

왜 LaunchedEffect를 사용할까?

Compose 화면이 사라질 때

```text
Coroutine 자동 취소
collect 자동 종료
메모리 누수 방지
```

를 해주기 때문이다.

<br>

## 9. derivedStateOf와 차이점

Compose 공부할 때 가장 많이 헷갈리는 부분이다.

### derivedStateOf

상태를 계산하기 위한 도구

```kotlin
val isButtonEnabled by remember {

    derivedStateOf {
        text.isNotBlank()
    }
}
```

동작

```text
상태 변경
↓
계산 수행
↓
UI 반영
```

<br>

### snapshotFlow

상태 변화를 감지하기 위한 도구

```kotlin
snapshotFlow {
    text
}.collect {

}
```

동작

```text
상태 변경
↓
이벤트 발생
↓
API 호출
로그 전송
페이지네이션
```

<br>

비교

| 구분 | derivedStateOf | snapshotFlow |
|--------|--------|--------|
| 목적 | 상태 계산 | 상태 감지 |
| 반환값 | State | Flow |
| 사용 위치 | UI | Coroutine |
| collect | 불가능 | 가능 |
| API 호출 | 부적합 | 적합 |

<br>

## 10. snapshotFlow 사용 시 주의사항

### 1) collect 안에서 무거운 작업 금지

```kotlin
snapshotFlow {
    state
}.collect {

    heavyWork()
}
```

State가 자주 변경되면 성능 문제가 발생할 수 있다.

<br>

### 2) 필요한 값만 감시하기

좋지 않은 예

```kotlin
snapshotFlow {
    uiState
}
```

좋은 예

```kotlin
snapshotFlow {
    uiState.keyword
}
```

불필요한 이벤트 발생을 줄일 수 있다.

<br>

### 3) distinctUntilChanged 사용하기

```kotlin
snapshotFlow {
    keyword
}
    .distinctUntilChanged()
    .collect {

    }
```

중복 값 방출을 방지할 수 있다.

실무에서는 거의 항상 같이 사용한다.

<br>



또한 검색 기능이 추가된다면

```text
검색어 입력
↓
snapshotFlow
↓
debounce
↓
검색 API 호출
```

패턴으로 사용할 수 있다.

<br>

## 11. 언제 사용하면 좋을까?

다음과 같은 상황이라면 snapshotFlow를 고려하면 된다.

- 스크롤 위치 추적
- 페이지네이션
- 검색어 감지
- API 호출
- 로그 전송
- Analytics 이벤트
- Compose State → Flow 변환

반대로

단순히 UI 계산만 필요하다면

```kotlin
derivedStateOf
```

를 사용하는 것이 더 적절하다.

<br>

## 12. 정리

- snapshotFlow는 Compose State를 Flow로 변환하는 함수이다.
- State 변경을 Coroutine에서 감지할 수 있다.
- API 호출, 로그 전송, 검색 기능 등에 활용된다.
- 보통 LaunchedEffect 안에서 사용한다.
- debounce, distinctUntilChanged 같은 Flow 연산자를 사용할 수 있다.
- derivedStateOf는 상태 계산용이다.
- snapshotFlow는 상태 변화 감지용이다.
- Compose와 Coroutine을 연결하는 대표적인 도구이다.
