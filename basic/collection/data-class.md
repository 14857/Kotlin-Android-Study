# Data Class

## 목차

1. Data Class란?
2. 일반 클래스와 차이점
3. Data Class 생성
4. Data Class가 자동 생성해주는 기능
5. copy()
6. 구조 분해 선언 (Destructuring)
7. 주의사항
8. 실무에서 사용하는 이유
9. 정리

<br>

# 1. Data Class란?

Data Class는 데이터를 저장하기 위한 클래스를 간단하게 만들 수 있는 코틀린 기능이다.

일반 클래스에서는 `toString()`, `equals()`, `hashCode()` 등을 직접 구현해야 하지만 Data Class는 컴파일러가 자동으로 생성해준다.

```kotlin
data class User(
    val name: String,
    val age: Int
)
```

주로 다음과 같은 경우에 사용한다.

- API 응답 모델
- UI State
- DTO
- Domain Model
- 설정 정보

<br>

# 2. 일반 클래스와 차이점

일반 클래스

```kotlin
class User(
    val name: String,
    val age: Int
)

val user = User("Kim", 29)

println(user)
```



```text
User@6d311334
```

객체 주소값만 출력된다.

<br>

Data Class

```kotlin
data class User(
    val name: String,
    val age: Int
)

val user = User("Kim", 29)

println(user)
```



```text
User(name=Kim, age=29)
```

객체의 프로퍼티 값이 보기 좋게 출력된다.

<br>

# 3. Data Class 생성

기본 형태

```kotlin
data class User(
    val name: String,
    val age: Int
)
```

객체 생성

```kotlin
val user = User(
    name = "Kim",
    age = 29
)
```

프로퍼티가 하나뿐이어도 Data Class를 사용할 수 있다.

```kotlin
data class Token(
    val accessToken: String
)
```

<br>

# 4. Data Class가 자동 생성해주는 기능

Data Class는 다음 함수들을 자동으로 생성한다.

| 함수 | 설명 |
|--------|--------|
| toString() | 객체 정보 출력 |
| equals() | 객체 값 비교 |
| hashCode() | 해시값 생성 |
| componentN() | 구조 분해 선언 |
| copy() | 객체 복사 |

<br>

## toString()

```kotlin
val user = User("Kim", 29)

println(user)
```



```text
User(name=Kim, age=29)
```

<br>

## equals()

```kotlin
val user1 = User("Kim", 29)
val user2 = User("Kim", 29)

println(user1 == user2)
```



```text
true
```

값이 같으면 같은 객체로 판단한다.

<br>

## componentN()

```kotlin
val user = User("Kim", 29)

val (name, age) = user

println(name)
println(age)
```



```text
Kim
29
```

<br>

# 5. copy()

Data Class에서 가장 많이 사용하는 기능이다.

기존 객체를 복사하면서 특정 값만 변경할 수 있다.

```kotlin
data class User(
    val name: String,
    val age: Int
)

val user1 = User("Kim", 29)

val user2 = user1.copy(
    age = 30
)

println(user1)
println(user2)
```



```text
User(name=Kim, age=29)
User(name=Kim, age=30)
```

<br>

Compose에서는 상태 변경 시 매우 자주 사용한다.

```kotlin
_uiState.update {
    it.copy(
        isLoading = true
    )
}
```

<br>

### 헷갈리기 쉬운 부분

`copy()`는 원본 객체를 수정하지 않는다.

```kotlin
val user1 = User("Kim", 29)

val user2 = user1.copy(
    age = 30
)
```



```text
user1 → User(name=Kim, age=29)
user2 → User(name=Kim, age=30)
```

새로운 객체를 생성하는 것이다.

<br>

# 6. 구조 분해 선언 (Destructuring)

객체를 여러 변수로 분리해서 사용할 수 있다.

```kotlin
val user = User("Kim", 29)

val (name, age) = user

println(name)
println(age)
```

<br>

일부 값만 사용할 수도 있다.

```kotlin
val (_, age) = user

println(age)
```

<br>

# 7. 주의사항

## 1) 주 생성자에 최소 1개의 프로퍼티가 필요하다

가능

```kotlin
data class User(
    val name: String
)
```

불가능

```kotlin
data class User()
```

<br>

## 2) equals() 비교는 생성자 프로퍼티만 사용한다

```kotlin
data class User(
    val name: String
) {
    var age: Int = 0
}
```

```kotlin
val user1 = User("Kim")
val user2 = User("Kim")

user1.age = 20
user2.age = 30

println(user1 == user2)
```



```text
true
```

`age`는 생성자 밖에 선언되었기 때문에 비교 대상에 포함되지 않는다.

실무에서도 자주 실수하는 부분이다.

<br>

## 3) Data Class는 상속용으로 사용하지 않는다

Data Class는 데이터를 저장하기 위한 객체이다.

그래서 다음 키워드를 사용할 수 없다.

- abstract
- open
- sealed
- inner

<br>

# 8. 실무에서 사용하는 이유

## API Response

```kotlin
data class UserResponse(
    val id: Long,
    val name: String,
    val email: String
)
```

<br>

## UI State

```kotlin
data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
```

<br>

## Compose 상태 관리

```kotlin
_uiState.update {
    it.copy(
        email = "test@test.com"
    )
}
```

<br>

Compose + MVVM 구조에서는 거의 대부분의 State를 Data Class로 관리한다.

<br>

# 9. 정리

- Data Class는 데이터를 저장하기 위한 클래스이다.
- `toString()`, `equals()`, `hashCode()`, `copy()` 등을 자동 생성한다.
- `copy()`를 이용하면 객체를 안전하게 복사할 수 있다.
- Compose의 State 관리에서 매우 자주 사용된다.
- API 모델, DTO, Domain Model 작성 시 거의 필수적으로 사용된다.

<br>

실무에서는 Data Class를 단순한 데이터 보관용 객체라고 생각하면 이해하기 쉽다.
