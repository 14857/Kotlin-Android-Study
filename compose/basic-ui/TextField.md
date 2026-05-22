# TextField

## TextField란?

`TextField`는 사용자의 입력을 받을 수 있는 입력 컴포넌트이다.

Compose의 `TextField`는 상태(State)와 연결해서 사용하는 것이 핵심이다.

```text
입력 → 상태 변경 → UI 자동 갱신
```

흐름으로 동작한다.

Compose에서는 상태(State)를 기반으로 UI를 그린다.

<br>

## 기본 TextField 사용법

```kotlin
@Composable
fun BasicTextFieldExample() {

    var text by remember {
        mutableStateOf("")
    }

    TextField(
        value = text,
        onValueChange = {
            text = it
        }
    )
}
```

<br>

## 코드 설명

### value

```kotlin
value = text
```

현재 TextField에 표시할 값을 의미한다. 현재 상태(State) 값을 화면에 표시한다.

<br>

### onValueChange

```kotlin
onValueChange = {
    text = it
}
```

사용자가 입력할 때마다 호출된다.

`it`에는 새롭게 입력된 값이 들어온다.

```text
사용자 입력 → 상태 변경
```

을 담당한다.

<br>

## 입력값 화면에 출력하기

```kotlin
@Composable
fun TextFieldExample() {

    var text by remember {
        mutableStateOf("")
    }

    Column {

        TextField(
            value = text,
            onValueChange = {
                text = it
            }
        )

        Text(
            text = "입력값: $text"
        )
    }
}
```

### 실행 흐름

1. 사용자가 TextField에 입력
2. `onValueChange` 호출
3. `text` 상태 변경
4. Compose 재구성(Recomposition)
5. UI 자동 갱신

```text
상태(State) 변경 → UI 다시 그리기가 Compose의 핵심 동작 방식이다.
```

<br>

## placeholder 사용

```kotlin
TextField(
    value = text,
    onValueChange = {
        text = it
    },
    placeholder = {
        Text("이름을 입력하세요")
    }
)
```

입력값이 비어있을 때 안내 문구를 표시한다.

<br>

## singleLine 사용

```kotlin
TextField(
    value = text,
    onValueChange = {
        text = it
    },
    singleLine = true
)
```

한 줄만 입력 가능하도록 설정한다.

로그인, 검색창 등에서 자주 사용한다.

<br>

## leadingIcon / trailingIcon

```kotlin
TextField(
    value = text,
    onValueChange = {
        text = it
    },
    leadingIcon = {
        Icon(
            imageVector = Icons.Default.Email,
            contentDescription = null
        )
    }
)
```

### leadingIcon

왼쪽 아이콘

### trailingIcon

오른쪽 아이콘

검색창, 비밀번호 입력창 등에서 자주 사용한다.

<br>

## enabled

```kotlin
TextField(
    value = text,
    onValueChange = {},
    enabled = false
)
```

입력을 비활성화한다.

<br>

## keyboardOptions

```kotlin
TextField(
    value = text,
    onValueChange = {
        text = it
    },
    keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number
    )
)
```

키보드 타입을 지정할 수 있다.

<br>

### 자주 사용하는 keyboardType

```kotlin
KeyboardType.Text
KeyboardType.Number
KeyboardType.Email
KeyboardType.Password
KeyboardType.Phone
```

<br>

## 실무에서 중요한 점

Compose의 `TextField`는 대부분 상태(State)와 함께 사용한다.

```text
TextField
→ 상태(State)
→ Recomposition
→ ViewModel
```

흐름과 매우 깊게 연결된다.

그래서 이후 배우게 되는:
- remember
- mutableStateOf
- State Hoisting
- ViewModel

개념과 자연스럽게 이어진다.

<br>

## 전체 예제

```kotlin
@Composable
fun TextFieldScreen() {

    var text by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        TextField(
            value = text,
            onValueChange = {
                text = it
            },
            placeholder = {
                Text("메시지를 입력하세요")
            },
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "입력값: $text"
        )
    }
}
```

## 참고

### 상태(State)

Compose에서 UI를 변경하기 위한 데이터이다.

```text
상태 변경 → UI 자동 갱신
```

방식으로 동작한다.

### 재구성(Recomposition)

상태(State)가 변경되었을 때 UI를 다시 그리는 과정이다.

Compose의 핵심 동작 방식 중 하나이다.
