# derivedStateOf

## 목차

1. derivedStateOf란?
2. 왜 사용하는가?
3. derivedStateOf 없이 구현하기
4. derivedStateOf 사용하기
5. 실전 예제 - 버튼 활성화
6. 실전 예제 - 스크롤 버튼 표시
7. remember와 함께 사용하는 이유
8. derivedStateOf 사용 시 주의사항
9. 언제 사용하면 좋을까?
10. 정리

<br>

## 1. derivedStateOf란?

`derivedStateOf`는 기존 State를 기반으로 새로운 State를 만드는 API이다.

쉽게 말하면 "이미 존재하는 상태를 이용해서 계산된 상태를 만들고 싶을 때" 사용한다.

예시

```kotlin
val text by remember {
    mutableStateOf("")
}

val isValid by remember {
    derivedStateOf {
        text.length >= 5
    }
}
```

여기서 text.length >= 5 는 text가 바뀔 때마다 계산된다.

결과적으로 isValid라는 계산된 State가 만들어진다.

<br>

## 2. 왜 사용하는가?

Compose는 State가 변경되면 리컴포지션(Recomposition)이 발생한다.

그런데 어떤 값은 직접 저장할 필요가 없다.

예시

```kotlin
val firstName = "Android"
val lastName = "Developer"
```

전체 이름은 "$firstName $lastName" 로 계산할 수 있다.

이런 값을 별도의 State로 관리하면

```kotlin
var fullName by remember {
    mutableStateOf("")
}
```

처럼 불필요한 코드가 늘어난다.

Compose에서는 "원본 State만 관리하고 나머지는 계산해서 사용" 하는 것이 좋다.

이때 사용하는 것이 derivedStateOf이다.

<br>

## 3. derivedStateOf 없이 구현하기

예시

```kotlin
var text by remember {
    mutableStateOf("")
}

val isValid = text.length >= 5
```

이렇게 작성해도 동작한다.

하지만 리컴포지션이 발생할 때마다 text.length >= 5 가 계속 계산된다.

간단한 계산은 문제가 없지만 계산 비용이 크거나 스크롤처럼 매우 자주 변경되는 상태를 다룰 때는 비효율적일 수 있다.

<br>

## 4. derivedStateOf 사용하기

```kotlin
var text by remember {
    mutableStateOf("")
}

val isValid by remember {
    derivedStateOf {
        text.length >= 5
    }
}
```

동작 순서

1. text 상태 생성
2. text 값 변경
3. derivedStateOf 재계산
4. 계산 결과가 바뀌면 리컴포지션 발생

즉 text.length >= 5 결과가 이전과 동일하면 불필요한 업데이트를 줄일 수 있다.

<br>

## 5. 실전 예제 - 버튼 활성화

### 로그인 버튼 활성화

회원가입, 로그인 화면에서 자주 사용하는 패턴이다.

```kotlin
@Composable
fun LoginScreen() {

    var text by remember {
        mutableStateOf("")
    }

    val buttonEnabled by remember {
        derivedStateOf {
            text.length >= 5
        }
    }

    Column {

        TextField(
            value = text,
            onValueChange = {
                text = it
            }
        )

        Button(
            onClick = {},
            enabled = buttonEnabled
        ) {
            Text("로그인")
        }
    }
}
```

동작

```text
abc 입력
→ 버튼 비활성화

abcde 입력
→ 버튼 활성화
```

<br>

## 6. 실전 예제 - 스크롤 버튼 표시

### TOP 버튼 표시

실무에서 매우 자주 등장하는 예제이다.

```kotlin
val listState = rememberLazyListState()

val showButton by remember {
    derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }
}
```

사용

```kotlin
if (showButton) {
    FloatingActionButton(
        onClick = {}
    ) {
        Text("TOP")
    }
}
```

동작

```text
맨 위
→ 버튼 숨김

아래로 스크롤
→ 버튼 표시
```

활용 예시

* TOP 버튼
* 툴바 숨김/표시
* 스크롤 진행률 표시
* FAB 노출 제어

<br>

## 7. remember와 함께 사용하는 이유

대부분 다음과 같이 사용한다.

```kotlin
val isValid by remember {
    derivedStateOf {
        text.length >= 5
    }
}
```

만약 다음과 같이 작성한다면,

```kotlin
val isValid by derivedStateOf {
    text.length >= 5
}
```

리컴포지션이 발생할 때마다 새로운 derivedStateOf 객체가 생성된다.

따라서 일반적으로 다음과 같은 형태를 사용한다.

```kotlin
remember {
    derivedStateOf { }
}
```


<br>

## 8. derivedStateOf 사용 시 주의사항

모든 계산에 사용할 필요는 없다.

예시

```kotlin
val result = count + 1
```

이 정도 계산은 derivedStateOf 를 사용할 이유가 없다.

오히려 코드만 복잡해진다.

### 좋은 예

```kotlin
val showButton by remember {
    derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }
}
```

### 굳이 필요 없는 예

```kotlin
val nextCount = count + 1
```

사용 기준은 다음과 같다.

* 계산 비용이 큰 경우
* 상태 변경이 매우 자주 발생하는 경우

<br>

## 9. 언제 사용하면 좋을까?

대표적인 사용 사례

### 버튼 활성화 여부

```kotlin
text.length >= 5
```

### 폼 유효성 검사

```kotlin
email.isNotBlank() &&
password.length >= 8
```

### 스크롤 상태 계산

```kotlin
listState.firstVisibleItemIndex > 0
```

### 툴바 표시 여부

```kotlin
scrollOffset > 100
```

### 여러 State 조합

```kotlin
firstName.isNotBlank() &&
lastName.isNotBlank()
```

### 총 금액 계산

```kotlin
price * quantity
```

<br>

## 10. 정리

* derivedStateOf는 기존 State를 기반으로 계산된 State를 만든다.
* 원본 State를 직접 수정하지 않는다.
* 계산 결과가 바뀌었을 때만 UI를 업데이트한다.
* 스크롤 상태나 폼 검증에 자주 사용된다.
* 대부분 remember와 함께 사용한다.
* 단순 계산에는 사용할 필요가 없다.

대표 패턴

```kotlin
val showButton by remember {
    derivedStateOf {
        listState.firstVisibleItemIndex > 0
    }
}
```

Compose에서 derivedStateOf를 보면 "원본 State를 이용해 계산된 State를 만드는구나" 라고 이해하면 된다.
