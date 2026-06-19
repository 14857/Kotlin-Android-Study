# clickable.md

<br>

목차

1. clickable이란?
2. Modifier.clickable 기본 사용법
3. 클릭 시 상태 변경하기
4. 클릭 가능한 Card 만들기
5. Ripple 효과란?
6. Ripple 제거하기
7. Long Click 처리하기
8. 중복 클릭 방지하기
9. clickable 사용 시 주의사항
10. 실무 예제
11. 정리

<br>

## 1. clickable이란?

Compose에서 View의 setOnClickListener() 역할을 하는 Modifier이다.

Android XML에서는 보통 다음과 같이 클릭 이벤트를 처리했다.

```kotlin
button.setOnClickListener {
    // 클릭 처리
}
```

Compose에서는 UI가 함수이기 때문에 Modifier.clickable()을 사용한다.

```kotlin
Modifier.clickable { }
```

왜 Modifier로 제공될까?

Compose에서는 UI 기능 대부분을 Modifier로 추가한다.

* 클릭 가능 여부
* 크기
* 패딩
* 배경색
* 애니메이션

모두 Modifier를 통해 조합할 수 있다.

<br>

## 2. Modifier.clickable 기본 사용법

```kotlin
@Composable
fun ClickableExample() {
    Text(
        text = "클릭하세요",
        modifier = Modifier.clickable {
            println("클릭됨")
        }
    )
}
```

클릭하면 lambda 내부 코드가 실행된다.

람다(lambda)는 나중에 실행될 코드를 전달하는 문법이다.

```kotlin
Modifier.clickable {
    println("클릭")
}
```

위 코드는

```kotlin
Modifier.clickable(
    onClick = {
        println("클릭")
    }
)
```

와 동일하다.

<br>

## 3. 클릭 시 상태 변경하기

실제로는 클릭 후 상태(State)를 변경하는 경우가 많다.

```kotlin
@Composable
fun CounterScreen() {

    var count by remember {
        mutableStateOf(0)
    }

    Text(
        text = "클릭 횟수 : $count",
        modifier = Modifier.clickable {
            count++
        }
    )
}
```

동작 과정

1. count = 0
2. Text 출력
3. 클릭
4. count 증가
5. Compose Recomposition 발생
6. UI 갱신

결과

```text
클릭 횟수 : 1
클릭 횟수 : 2
클릭 횟수 : 3
```

왜 remember를 사용할까?

Compose는 화면이 다시 그려질 때 일반 변수 값을 유지하지 않는다.

```kotlin
var count = 0
```

이렇게 작성하면 Recomposition마다 초기화된다.

따라서 상태 저장이 필요한 값은 remember를 사용해야 한다.

<br>

## 4. 클릭 가능한 Card 만들기

실무에서 가장 많이 사용하는 형태이다.

```kotlin
@Composable
fun CardItem(
    title: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(16.dp)
        )
    }
}
```

사용

```kotlin
CardItem(
    title = "회의록"
) {
    println("카드 클릭")
}
```

차곡 프로젝트에서도 카드 선택 화면이나 목록 화면에서 자주 사용하는 패턴이다.

<br>

## 5. Ripple 효과란?

Android에서 클릭하면 퍼져나가는 물결 효과를 Ripple이라고 한다.

```kotlin
Modifier.clickable {
    onClick()
}
```

기본적으로 Ripple 효과가 자동 적용된다.

사용자가

* 클릭이 되었는지
* 터치가 인식되었는지

확인할 수 있기 때문에 UX 측면에서 중요하다.

<br>

## 6. Ripple 제거하기

가끔 디자인 요구사항 때문에 Ripple을 제거해야 한다.

```kotlin
Modifier.clickable(
    interactionSource = remember {
        MutableInteractionSource()
    },
    indication = null
) {
    onClick()
}
```

왜 사용할까?

예시

* 배경 애니메이션이 이미 있는 경우
* 커스텀 터치 효과가 있는 경우
* 디자인 시스템에서 Ripple 금지

다만 특별한 이유가 없다면 Ripple 유지가 권장된다.

<br>

## 7. Long Click 처리하기

clickable은 Long Click을 지원하지 않는다.

이 경우 combinedClickable을 사용한다.

```kotlin
Modifier.combinedClickable(
    onClick = {
        println("일반 클릭")
    },
    onLongClick = {
        println("길게 클릭")
    }
)
```

예시

메신저

```text
클릭 → 채팅방 입장
길게 클릭 → 메뉴 표시
```

파일 탐색기

```text
클릭 → 파일 열기
길게 클릭 → 선택 모드 진입
```

실무에서 꽤 자주 사용한다.

<br>

## 8. 중복 클릭 방지하기

초보자가 자주 만드는 버그

```text
버튼 연타
↓
API 여러 번 호출
↓
데이터 중복 생성
```

예시

```kotlin
var enabled by remember {
    mutableStateOf(true)
}

Button(
    enabled = enabled,
    onClick = {
        enabled = false

        // API 호출
    }
) {
    Text("저장")
}
```

또는 ViewModel에서 처리한다.

```kotlin
if (uiState.isLoading) return
```

실무에서는 UI보다 ViewModel에서 한 번 더 막아주는 것이 안전하다.

<br>

## 9. clickable 사용 시 주의사항

Modifier 순서에 따라 클릭 영역이 달라진다.

예시

```kotlin
Modifier
    .padding(16.dp)
    .clickable { }
```

클릭 가능한 영역

```text
패딩 제외
```

반면

```kotlin
Modifier
    .clickable { }
    .padding(16.dp)
```

클릭 가능한 영역

```text
패딩 포함
```

Modifier는 위에서 아래 순서대로 적용된다.

Compose 입문자가 가장 많이 헷갈리는 부분 중 하나다.

<br>

## 10. 실무 예제

목록 화면

```kotlin
LazyColumn {
    items(items) { item ->

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    onItemClick(item.id)
                }
        ) {
            Text(
                text = item.title,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
```

동작

```text
사용자 클릭
↓
아이템 ID 전달
↓
상세 화면 이동
```

Compose 앱에서 매우 흔한 패턴이다.

<br>

## 11. 정리

* Compose의 클릭 이벤트는 Modifier.clickable()로 처리한다.
* XML의 setOnClickListener()와 비슷한 역할이다.
* 클릭 후 상태 변경 시 remember와 함께 사용하는 경우가 많다.
* Card, Row, Column 등 대부분의 Composable에 적용할 수 있다.
* 기본적으로 Ripple 효과가 제공된다.
* Ripple 제거는 interactionSource와 indication을 사용한다.
* Long Click은 combinedClickable을 사용한다.
* 버튼 연타로 인한 중복 요청을 방지해야 한다.
* Modifier 순서에 따라 클릭 영역이 달라진다.
* 실무에서는 리스트 아이템, 카드, 메뉴 선택 등에 매우 자주 사용된다.
