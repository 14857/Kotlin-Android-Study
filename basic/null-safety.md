# Null Safety

## 목차

1. Null Safety란?
2. Nullable 타입
3. Safe Call 연산자 (`?.`)
4. Elvis 연산자 (`?:`)
5. Not Null Assertion (`!!`)
6. Safe Cast (`as?`)
7. let 함수
8. 정리

<br>

## 1. Null Safety란?

코틀린은 NullPointerException(NPE)을 줄이기 위해 타입 시스템 차원에서 Null Safety를 지원한다.

Java에서는 다음과 같은 코드가 가능하다.

```java
String name = null;

System.out.println(name.length());
```

실행 시:

```text
NullPointerException
```

반면 코틀린은 기본적으로 null을 허용하지 않는다.

```kotlin
val name: String = null
```

컴파일 오류:

```text
Null can not be a value of a non-null type String
```

코틀린은 컴파일 시점에 null 관련 오류를 최대한 방지한다.

<br>

## 2. Nullable 타입

null을 허용하려면 타입 뒤에 `?`를 붙인다.

```kotlin
val name: String? = null
```

### Non-Nullable

```kotlin
val name: String = "Kim"
```

null 저장 불가

### Nullable

```kotlin
val name: String? = null
```

null 저장 가능

### 차이

```kotlin
val name: String = "Kim"

println(name.length)
```

가능

```kotlin
val name: String? = "Kim"

println(name.length)
```

컴파일 오류

```text
Only safe (?.) or non-null asserted (!!.) calls are allowed
```

Nullable 타입은 null 가능성을 고려해야 한다.

<br>

## 3. Safe Call 연산자 (`?.`)

객체가 null이 아닐 때만 접근한다.

```kotlin
val name: String? = "Kim"

println(name?.length)
```

결과

```text
3
```

---

```kotlin
val name: String? = null

println(name?.length)
```

결과

```text
null
```

### Java 스타일 비교

```java
if(name != null){
    System.out.println(name.length());
}
```

↓

```kotlin
println(name?.length)
```

코틀린에서 매우 자주 사용하는 문법이다.

<br>

## 4. Elvis 연산자 (`?:`)

null인 경우 기본값을 반환한다.

```kotlin
val name: String? = null

val result = name ?: "Unknown"

println(result)
```

결과

```text
Unknown
```

### 사용 예시

```kotlin
val length = name?.length ?: 0
```

의미

```text
name이 null이면 0 반환
null이 아니면 length 반환
```

### return과 함께 사용

```kotlin
fun printName(name: String?) {
    val value = name ?: return

    println(value)
}
```

실무에서 자주 사용하는 패턴이다.

<br>

## 5. Not Null Assertion (`!!`)

개발자가 null이 아님을 강제로 보장한다.

```kotlin
val name: String? = "Kim"

println(name!!.length)
```

결과

```text
3
```

하지만 null이라면 예외가 발생한다.

```kotlin
val name: String? = null

println(name!!.length)
```

실행 결과

```text
NullPointerException
```

### 주의

```kotlin
!!
```

는 Null Safety를 우회하는 문법이다.

가능하면 사용을 최소화하는 것이 좋다.

<br>

## 6. Safe Cast (`as?`)

캐스팅 실패 시 null을 반환한다.

```kotlin
val value: Any = "Hello"

val text = value as? String

println(text)
```

결과

```text
Hello
```

### 실패하는 경우

```kotlin
val value: Any = 123

val text = value as? String

println(text)
```

결과

```text
null
```

### 일반 캐스팅

```kotlin
val text = value as String
```

실패 시 예외 발생

```text
ClassCastException
```

### Safe Cast

```kotlin
val text = value as? String
```

실패 시

```text
null
```

<br>

## 7. let 함수

Nullable 객체가 null이 아닐 때만 코드를 실행한다.

```kotlin
val name: String? = "Kim"

name?.let {
    println(it)
}
```

결과

```text
Kim
```

---

null인 경우

```kotlin
val name: String? = null

name?.let {
    println(it)
}
```

아무것도 실행되지 않는다.

### Java 스타일 비교

```java
if(name != null){
    System.out.println(name);
}
```

↓

```kotlin
name?.let {
    println(it)
}
```

### 실무 예시

```kotlin
user?.let {
    viewModel.saveUser(it)
}
```

```kotlin
imageUrl?.let {
    loadImage(it)
}
```

Compose, Android 개발에서 매우 자주 등장한다.

<br>

## 정리

| 문법        | 설명             |
| --------- | -------------- |
| `String`  | null 불가        |
| `String?` | null 허용        |
| `?.`      | Safe Call      |
| `?:`      | Elvis Operator |
| `!!`      | 강제 Non-Null    |
| `as?`     | Safe Cast      |
| `let`     | null이 아닐 때 실행  |

<br>

## 예제

```kotlin
val name: String? = null

val length = name?.length ?: 0

println(length)
```

실행 결과

```text
0
```

### 동작 과정

1. `name`이 null인지 확인
2. null이면 `name?.length` 결과는 null
3. Elvis 연산자(`?:`) 실행
4. 기본값 `0` 반환

<br>

## 참고

* Kotlin 공식 문서: Null Safety
* Kotlin 공식 문서: Type Casts
* Kotlin 공식 문서: Scope Functions
