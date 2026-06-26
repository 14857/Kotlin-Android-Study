# BasicTextField

## 목차

1. BasicTextField란?
2. TextField와의 차이
3. 언제 사용할까?
4. 가장 기본적인 사용법
5. value와 onValueChange 이해하기
6. decorationBox란?
7. Placeholder 직접 만들기
8. 검색창 만들기
9. 입력 길이 제한하기
10. 실무에서 자주 사용하는 예제
11. BasicTextField 사용 시 주의사항
12. 정리

<br>

## 1. BasicTextField란?

`BasicTextField`는 Compose에서 가장 기본적인 입력 컴포넌트이다.

Material3의 `TextField`처럼 배경, 테두리, Placeholder 등이 자동으로 제공되지 않는다.

오직 **텍스트 입력 기능만 제공**하기 때문에 원하는 UI를 자유롭게 만들 수 있다.

주로 다음과 같은 경우에 사용한다

* 입력 기능만 필요하다.
* 디자인을 직접 만들고 싶다.
* Material 디자인을 사용하지 않는다.


<br>

## 2. TextField와의 차이

| TextField       | BasicTextField |
| --------------- | -------------- |
| Material 디자인 제공 | 디자인 제공 안 함     |
| Placeholder 제공  | 직접 구현          |
| Label 제공        | 직접 구현          |
| Border 제공       | 직접 구현          |
| 배경 제공           | 직접 구현          |
| 빠르게 사용 가능       | 자유도가 매우 높음     |

예를 들어

TextField는

```kotlin
TextField(
    value = text,
    onValueChange = { text = it }
)
```

만으로 완성된 입력창이 나온다.

반면 BasicTextField는

```kotlin
BasicTextField(
    value = text,
    onValueChange = { text = it }
)
```

텍스트만 입력되고 아무런 디자인도 없다.

<br>

## 3. 언제 사용할까?

실무에서는 아래처럼 디자인이 조금이라도 특별하면 대부분 BasicTextField를 사용한다.

* 검색창
* 로그인 입력창
* OTP 입력창
* 채팅 입력창
* 태그 입력
* 커스텀 Placeholder
* 완전히 새로운 디자인

반대로 Material 디자인 그대로 사용한다면 TextField가 더 편하다.

<br>

## 4. 가장 기본적인 사용법

```kotlin
@Composable
fun BasicTextFieldExample() {

    var text by remember {
        mutableStateOf("")
    }

    BasicTextField(
        value = text,
        onValueChange = {
            text = it
        }
    )
}
```

### 코드 설명

```kotlin
var text by remember {
    mutableStateOf("")
}
```

입력값을 저장하는 State이다.

사용자가 입력하면 계속 값이 변경된다.

```kotlin
value = text
```

현재 화면에 보여줄 문자열이다.

```kotlin
onValueChange = {
    text = it
}
```

사용자가 입력할 때마다 호출된다.

`it`에는 새로운 문자열이 들어온다.

<br>

## 5. value와 onValueChange 이해하기

Compose는 State 기반 UI이다.

사용자가 A를 입력하면 text = "A"가 된다.

다음으로 AB를 입력하면 text = "AB"가 된다.

즉,

```
사용자 입력

↓

onValueChange 호출

↓

State 변경

↓

UI 다시 그림(Recomposition)
```

이 과정이 반복된다.

<br>

## 6. decorationBox란?

`decorationBox`는 BasicTextField의 가장 중요한 기능이다.

입력창 주변 UI를 직접 만들 수 있다.

예를 들면

* Placeholder
* Icon
* Border
* Background
* Padding

모두 decorationBox에서 만든다.

```kotlin
BasicTextField(
    value = text,
    onValueChange = {
        text = it
    },
    decorationBox = { innerTextField ->

        Box(
            modifier = Modifier
                .border(1.dp, Color.Gray)
                .padding(12.dp)
        ) {

            innerTextField()
        }
    }
)
```

### innerTextField()가 중요한 이유

innerTextField()가 실제 입력창이다.

이걸 호출하지 않으면 텍스트 입력 자체가 사라진다.

많이 하는 실수 중 하나이다.

<br>

## 7. Placeholder 직접 만들기

```kotlin
BasicTextField(
    value = text,
    onValueChange = {
        text = it
    },
    decorationBox = { innerTextField ->

        Box(
            modifier = Modifier.padding(12.dp)
        ) {

            if (text.isEmpty()) {
                Text(
                    text = "검색어를 입력하세요.",
                    color = Color.Gray
                )
            }

            innerTextField()
        }
    }
)
```

동작 순서는

```
text가 비어있음

↓

Placeholder 출력

↓

입력 시작

↓

Placeholder 사라짐
```

TextField에서는 자동으로 제공하지만

BasicTextField에서는 직접 만들어야 한다.

<br>

## 8. 검색창 만들기

```kotlin
BasicTextField(
    value = keyword,
    onValueChange = {
        keyword = it
    },
    decorationBox = { innerTextField ->

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box {

                if (keyword.isEmpty()) {
                    Text("검색")
                }

                innerTextField()
            }
        }
    }
)
```

실무에서 검색창을 만들 때 가장 많이 사용하는 방식이다.

<br>

## 9. 입력 길이 제한하기

예를 들어 최대 10글자만 입력하고 싶다면

```kotlin
BasicTextField(
    value = text,
    onValueChange = {

        if (it.length <= 10) {
            text = it
        }

    }
)
```

11번째 글자를 입력하려고 하면 State를 변경하지 않기 때문에 화면도 변경되지 않는다.

<br>

## 10. 실무에서 자주 사용하는 예제

### 검색창

* Placeholder
* Search Icon

<br>

### 로그인

* 이메일 입력
* 비밀번호 입력

<br>

### 채팅

* 메시지 입력창
* 전송 버튼

<br>

### OTP 입력

* 숫자만 입력
* 글자 수 제한

<br>

### 닉네임 입력

* 최대 글자 수
* 특수문자 제한

<br>

## 11. BasicTextField 사용 시 주의사항

### 1) Placeholder는 자동으로 생기지 않는다.

직접 구현해야 한다.

<br>

### 2) Border도 없다.

```kotlin
.border(...)
```

를 직접 추가해야 한다.

<br>

### 3) Background도 없다.

```kotlin
.background(...)
```

직접 추가해야 한다.

<br>

### 4) Padding도 없다.

직접 넣어야 한다.

<br>

### 5) innerTextField()를 반드시 호출해야 한다.

```kotlin
decorationBox = { innerTextField ->

    Box {

        innerTextField()

    }
}
```

이 코드를 빼먹으면 입력창이 보이지 않는다.

<br>

## 12. 정리

* BasicTextField는 입력 기능만 제공하는 가장 기본적인 입력 컴포넌트이다.
* TextField보다 자유도가 훨씬 높다.
* Placeholder, Border, Background는 직접 구현해야 한다.
* decorationBox를 이용해 원하는 입력 UI를 만들 수 있다.
* 실제 앱에서는 검색창, 로그인, 채팅, OTP 입력 등 커스텀 디자인이 필요한 경우 BasicTextField를 많이 사용한다.
* `innerTextField()`를 호출하지 않으면 실제 입력창이 표시되지 않는다.
* Material 디자인을 그대로 사용할 때는 TextField, 커스텀 UI를 만들 때는 BasicTextField를 사용하는 것이 일반적이다.
