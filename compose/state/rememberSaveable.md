# rememberSaveable

## rememberSaveable란?

rememberSaveable은 Compose에서 상태(State)를 저장하고 복원하기 위한 함수이다.

`remember`와 비슷하게 상태를 저장하지만,
화면 회전(Configuration Change)이나 프로세스 재생성 상황에서도 값을 유지할 수 있다.

<br>

## remember와의 차이

### remember

```kotlin
var text by remember {
    mutableStateOf("")
}
```

특징:

- Composable이 다시 그려질 때 상태 유지
- 화면 회전 시 값 초기화
- Activity 재생성 시 상태 사라짐

<br>

### rememberSaveable

```kotlin
var text by rememberSaveable {
    mutableStateOf("")
}
```

특징:

- 화면 회전 시 상태 유지
- 프로세스 재생성 상황에서도 복원 가능
- Android Bundle을 사용해 값 저장

<br>

## 왜 필요한가?

안드로이드에서는 화면 회전 시 Activity가 다시 생성된다.

```text
세로 모드
   ↓
가로 모드 회전
   ↓
Activity 재생성
   ↓
State 초기화
```

이때 `remember`만 사용하면 기존 값이 사라진다.

<br>

예시:

```kotlin
@Composable
fun NameScreen() {

    var name by remember {
        mutableStateOf("")
    }

    TextField(
        value = name,
        onValueChange = {
            name = it
        }
    )
}
```

텍스트 입력 후 화면 회전 시 값이 초기화된다.

remember를 rememberSaveable로 바꾸면 화면 회전 후에도 값이 유지된다.

<br>

## 동작 방식

rememberSaveable은 내부적으로 Android의 `savedInstanceState`를 사용한다.

```text
State 저장
   ↓
Bundle 저장
   ↓
화면 재생성
   ↓
Bundle 복원
   ↓
State 복구
```

따라서 Activity 재생성 이후에도 상태를 유지할 수 있다.

<br>

## rememberSaveable이 가능한 타입

기본적으로 Bundle에 저장 가능한 타입을 지원한다.

예시:

- String
- Int
- Boolean
- Float
- Double
- Parcelable
- Serializable

<br>

예시:

```kotlin
var count by rememberSaveable {
    mutableIntStateOf(0)
}
```

<br>

## TextField와 함께 사용

Compose에서 가장 많이 사용하는 패턴 중 하나이다.

```kotlin
@Composable
fun LoginScreen() {

    var email by rememberSaveable {
        mutableStateOf("")
    }

    var password by rememberSaveable {
        mutableStateOf("")
    }

    Column {

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            }
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            }
        )
    }
}
```

로그인 화면에서 입력값 유지에 자주 사용된다.

<br>

## remember와 rememberSaveable 비교

| 항목 | remember | rememberSaveable |
|---|---|---|
| Recomposition 유지 | O | O |
| 화면 회전 유지 | X | O |
| Activity 재생성 유지 | X | O |
| Bundle 저장 사용 | X | O |

<br>

## 언제 사용해야 할까?

### remember 사용

- 일시적인 UI 상태
- 화면 회전 시 유지 필요 없음
- 애니메이션 상태
- 스크롤 상태 일부


### rememberSaveable 사용

- 사용자 입력값
- 검색어
- 체크 상태
- 화면 회전 후에도 유지되어야 하는 값

<br>

## 주의할 점

rememberSaveable은 큰 데이터를 저장하는 용도로 사용하면 안 된다.

이유:

- Bundle 크기 제한 존재
- 성능 문제 발생 가능

큰 데이터는 다음과 같은 곳에서 관리하는 것이 좋다.

- ViewModel
- Repository
- Database

<br>

## ViewModel과의 차이

rememberSaveable은 UI 상태 저장에 적합하다.

하지만 화면 전체 상태 관리나 비즈니스 로직은 ViewModel에서 처리한다.

```text
rememberSaveable
→ 간단한 UI 상태 저장

ViewModel
→ 화면 상태 및 비즈니스 로직 관리
```

<br>

## 정리

- rememberSaveable은 상태를 저장하고 복원하는 함수이다.
- 화면 회전 후에도 상태를 유지할 수 있다.
- 내부적으로 Bundle(savedInstanceState)을 사용한다.
- TextField 입력 상태 저장에 자주 사용된다.
- remember보다 더 오래 상태를 유지할 수 있다.
- 큰 데이터 저장에는 적합하지 않다.
- 복잡한 상태 관리는 ViewModel을 사용하는 것이 좋다.
