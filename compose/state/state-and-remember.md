# State와 remember

## State란?

Compose에서 화면(UI)은 상태(State)에 따라 결정된다.

상태 값이 변경되면 Compose는 해당 UI를 자동으로 다시 그린다(Recomposition).

예시:

- 버튼 클릭 횟수
- TextField 입력값
- 체크박스 선택 여부
- 로딩 상태

<br>

## 왜 일반 var를 사용하면 안 될까?

```kotlin
var count = 0
```

이렇게 선언하면 값은 바뀌지만 Compose는 값이 변경된 사실을 감지하지 못한다.

→ 화면이 자동으로 업데이트되지 않는다.

Compose는 상태 변화를 감지할 수 있는 특별한 상태 객체가 필요하다.

<br>

## mutableStateOf

Compose에서 상태를 저장할 때 사용하는 함수이다.

```kotlin
var count by mutableStateOf(0)
```

상태 값이 변경되면 Compose가 자동으로 UI를 다시 그린다.

<br>

## remember

`remember`는 Recomposition이 발생해도 값을 유지해주는 함수이다.

```kotlin
val count = remember {
    mutableStateOf(0)
}
```

만약 `remember` 없이 상태를 만들면 Recomposition 때마다 값이 초기화된다.

<br>

## State 기본 구조

```kotlin
var count by remember {
    mutableStateOf(0)
}
```

구성 요소:

- `mutableStateOf()`
  → Compose가 감지 가능한 상태 생성

- `remember`
  → Recomposition 동안 상태 유지

- `by`
  → `.value` 없이 값 사용 가능

<br>

## 버튼 클릭 예제

```kotlin
@Composable
fun CounterScreen() {

    var count by remember {
        mutableStateOf(0)
    }

    Column {
        Text(text = "현재 숫자 : $count")

        Button(
            onClick = {
                count++
            }
        ) {
            Text("증가")
        }
    }
}
```

동작 과정:

1. 버튼 클릭
2. count 값 변경
3. Compose가 상태 변화를 감지
4. UI 다시 그림(Recomposition)
5. Text가 최신 값으로 변경

<br>

## TextField 상태 관리

```kotlin
@Composable
fun NameInput() {

    var name by remember {
        mutableStateOf("")
    }

    Column {
        TextField(
            value = name,
            onValueChange = {
                name = it
            }
        )

        Text(text = "입력값 : $name")
    }
}
```

`onValueChange`에서 상태를 변경해야 입력값이 유지된다.

<br>

## Recomposition이란?

상태(State)가 변경될 때 Compose가 필요한 UI만 다시 그리는 과정이다.

```text
상태 변경
→ Compose가 감지
→ 필요한 UI만 다시 실행
→ 화면 업데이트
```

Compose는 전체 화면을 다시 그리는 것이 아니라 필요한 부분만 다시 그린다.

<br>

## rememberSaveable

`remember`는 화면 회전 시 상태가 사라진다.

화면 회전에도 상태를 유지하려면 `rememberSaveable`을 사용한다.

```kotlin
var name by rememberSaveable {
    mutableStateOf("")
}
```

사용 예시:

- TextField 입력값 유지
- 선택 상태 유지
- 간단한 UI 상태 저장

<br>

## remember vs rememberSaveable

| 함수 | Recomposition | 화면 회전 |
|---|---|---|
| remember | 유지됨 | 초기화됨 |
| rememberSaveable | 유지됨 | 유지됨 |

<br>

## 정리

- Compose UI는 State 기반으로 동작한다.
- 상태가 변경되면 UI가 자동으로 업데이트된다.
- `mutableStateOf()`로 상태를 만든다.
- `remember`로 상태를 유지한다.
- 상태 변경 시 Recomposition이 발생한다.
- 화면 회전까지 유지하려면 `rememberSaveable`을 사용한다.

<br>

## 참고

- Android Developers Compose State
- Jetpack Compose 공식 문서
- State and Jetpack Compose
