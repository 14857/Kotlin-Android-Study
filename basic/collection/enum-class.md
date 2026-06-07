# Enum Class

## 목차

1. Enum Class란?
2. Enum Class 생성
3. Enum 값 사용하기
4. when과 함께 사용하기
5. 프로퍼티 추가하기
6. 함수 추가하기
7. values()와 entries
8. valueOf()
9. 실무 사용 예시
10. 정리

<br>

# 1. Enum Class란?

Enum Class는 미리 정해진 상수(Constant) 집합을 표현할 때 사용하는 클래스이다.

예를 들어 회원 상태가 다음 중 하나만 가능하다고 가정해보자.

- ACTIVE
- INACTIVE
- BLOCKED

이럴 때 문자열로 관리하면 오타가 발생할 수 있다.

```kotlin
val status = "ACTVE"
```

컴파일 오류도 발생하지 않는다.

<br>

Enum Class를 사용하면 가능한 값을 제한할 수 있다.

```kotlin
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED
}
```

<br>

# 2. Enum Class 생성

기본 형태

```kotlin
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED
}
```

<br>

객체 생성이 아니라 Enum 값을 사용한다.

```kotlin
val status = UserStatus.ACTIVE
```

<br>

# 3. Enum 값 사용하기

```kotlin
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED
}

val status = UserStatus.ACTIVE

println(status)
```



```text
ACTIVE
```

<br>

비교도 가능하다.

```kotlin
if (status == UserStatus.ACTIVE) {
    println("활성 사용자")
}
```

<br>

# 4. when과 함께 사용하기

Enum은 when과 매우 자주 사용된다.

```kotlin
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED
}

val status = UserStatus.ACTIVE

when (status) {
    UserStatus.ACTIVE -> println("활성")
    UserStatus.INACTIVE -> println("비활성")
    UserStatus.BLOCKED -> println("차단")
}
```

<br>

Enum을 사용하면 모든 경우를 처리하도록 컴파일러가 확인해준다.

```kotlin
when (status) {
    UserStatus.ACTIVE -> {}
    UserStatus.INACTIVE -> {}
}
```

오류 발생

```text
'BLOCKED' case가 처리되지 않음
```

<br>

그래서 문자열보다 훨씬 안전하다.

<br>

# 5. 프로퍼티 추가하기

Enum도 클래스이기 때문에 프로퍼티를 가질 수 있다.

```kotlin
enum class UserRole(
    val description: String
) {
    ADMIN("관리자"),
    USER("일반 사용자"),
    GUEST("게스트")
}
```

사용

```kotlin
println(UserRole.ADMIN.description)
```



```text
관리자
```

<br>

# 6. 함수 추가하기

Enum 내부에 함수를 선언할 수도 있다.

```kotlin
enum class UserRole(
    val description: String
) {
    ADMIN("관리자"),
    USER("일반 사용자");

    fun isAdmin(): Boolean {
        return this == ADMIN
    }
}
```

사용

```kotlin
println(UserRole.ADMIN.isAdmin())
```



```text
true
```

<br>

# 7. values()와 entries

모든 Enum 값을 가져올 수 있다.

<br>

## values()

```kotlin
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED
}

UserStatus.values().forEach {
    println(it)
}
```



```text
ACTIVE
INACTIVE
BLOCKED
```

<br>

## entries (권장)

Kotlin 1.9부터는 entries 사용이 권장된다.

```kotlin
UserStatus.entries.forEach {
    println(it)
}
```

<br>

실무에서는 entries를 사용하는 경우가 많다.

<br>

# 8. valueOf()

문자열을 Enum으로 변환한다.

```kotlin
val status = UserStatus.valueOf("ACTIVE")

println(status)
```



```text
ACTIVE
```

<br>

존재하지 않는 값이면 예외가 발생한다.

```kotlin
UserStatus.valueOf("TEST")
```



```text
IllegalArgumentException
```

<br>

실무에서는 안전하게 처리하는 경우가 많다.

```kotlin
val status = runCatching {
    UserStatus.valueOf(value)
}.getOrNull()
```

<br>

# 9. 실무 사용 예시

## 사용자 상태

```kotlin
enum class UserStatus {
    ACTIVE,
    INACTIVE,
    BLOCKED
}
```

<br>

## 정렬 방식

```kotlin
enum class SortType {
    LATEST,
    POPULAR,
    NAME
}
```

<br>

## 언어 설정

```kotlin
enum class Language {
    KOREAN,
    ENGLISH,
    JAPANESE
}
```

<br>

## Compose State

```kotlin
data class UiState(
    val status: UserStatus = UserStatus.ACTIVE
)
```

<br>

실무에서는 문자열 대신 Enum을 사용하는 경우가 매우 많다.

<br>

# 10. 정리

- Enum Class는 정해진 상수 집합을 표현한다.
- 문자열보다 타입 안정성이 높다.
- when과 함께 자주 사용된다.
- 프로퍼티와 함수를 가질 수 있다.
- 모든 Enum 값 조회는 `entries` 사용을 권장한다.
- 상태값, 역할, 정렬 방식 등을 표현할 때 자주 사용된다.

<br>

# 헷갈리기 쉬운 포인트

## Enum은 객체를 생성하지 않는다

일반 클래스

```kotlin
val user = User()
```

<br>

Enum

```kotlin
val status = UserStatus.ACTIVE
```

Enum 값은 이미 생성되어 있는 객체를 사용하는 것이다.

<br>

## 문자열과 Enum은 다르다

```kotlin
val status = UserStatus.ACTIVE
```

```kotlin
if (status == "ACTIVE")
```

컴파일 오류

<br>

올바른 비교

```kotlin
if (status == UserStatus.ACTIVE)
```

<br>

## 상태값은 String보다 Enum이 좋다

```kotlin
val status = "ACTIVE"
```

오타 가능

```kotlin
val status = UserStatus.ACTIVE
```

컴파일러가 검사 가능

<br>

상태, 역할, 타입과 같이 정해진 값들만 사용해야 하는 경우에는 Enum Class를 사용하는 것이 가장 안전하다.
