# Side Effect

## Side Effect란?

Compose는 UI를 그리는 것이 목적이다.

하지만 실제 앱에서는 UI 외에도 다양한 작업이 필요하다.

예시:

- API 호출
- Snackbar 표시
- 로그 출력
- 애니메이션 시작
- 리스너 등록
- Coroutine 실행

이런 작업들을 Side Effect라고 한다.

<br>

## 왜 필요한가?

Compose는 상태(State)가 변경될 때마다 화면을 다시 실행(Recomposition)한다.

따라서 Composable 내부에 일반 코드처럼 작성하면 여러 번 실행될 수 있다.

```kotlin
@Composable
fun MyScreen() {

    println("실행됨")

}
```

이런 문제를 안전하게 처리하기 위해 Side Effect API를 사용한다.

<br>

## Compose와 Recomposition

Compose는 선언형 UI 방식이다.

상태(State)가 변경되면 화면을 다시 그린다.

```text
State 변경
→ Recomposition 발생
→ Composable 다시 실행
```

따라서 UI 외 작업은 별도로 관리해야 한다.

<br>

## 대표적인 Side Effect API

### LaunchedEffect

Coroutine 실행 및 초기 작업 처리에 사용한다.

```kotlin
LaunchedEffect(Unit) {

}
```

사용 예시:

- API 호출
- delay
- 초기 데이터 로딩

<br>

### DisposableEffect

리스너 등록 및 해제가 필요한 작업에 사용한다.

```kotlin
DisposableEffect(Unit) {

    onDispose {

    }

}
```

사용 예시:

- BroadcastReceiver
- Sensor Listener
- Lifecycle Observer

<br>

### SideEffect

Recomposition 이후 실행되는 작업에 사용한다.

```kotlin
SideEffect {

}
```

사용 예시:

- 로그 출력
- 외부 객체 상태 업데이트

<br>

### rememberCoroutineScope

이벤트 기반 Coroutine 실행에 사용한다.

```kotlin
val scope = rememberCoroutineScope()
```

사용 예시:

- 버튼 클릭
- Snackbar 표시
- 사용자 액션 처리

<br>

### rememberUpdatedState

최신 상태(State)를 안전하게 참조할 때 사용한다.

```kotlin
rememberUpdatedState(value)
```

<br>

## 언제 사용할까?

| API | 사용 목적 |
|---|---|
| LaunchedEffect | 화면 진입 시 작업 실행 |
| DisposableEffect | 리스너 등록/해제 |
| SideEffect | Recomposition 이후 작업 |
| rememberCoroutineScope | 사용자 이벤트 처리 |
| rememberUpdatedState | 최신 상태 참조 |

<br>

## 실무 예시

```text
앱 시작
→ API 호출
→ LaunchedEffect
```

```text
화면 종료
→ 리스너 제거
→ DisposableEffect
```

```text
버튼 클릭
→ Snackbar 표시
→ rememberCoroutineScope
```

<br>

## 핵심 정리

```text
Compose는 UI를 그리는 역할
→ UI 외 작업은 Side Effect로 처리
```

<br>

## 참고

- Compose에서 매우 중요한 개념 중 하나
- Recomposition과 함께 이해해야 함
- 대부분 Coroutine과 함께 사용
- Side Effect API마다 목적이 다름
