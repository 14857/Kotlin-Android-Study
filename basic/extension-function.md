# Extension Function

## 목차

1. Extension Function이란?
2. 왜 사용할까?
3. 기본 사용법
4. 매개변수 사용하기
5. 반환값 사용하기
6. 확장 함수와 클래스 멤버 함수
7. 실무 예시
8. 다른 언어와 차이점
9. 주의할 점
10. 정리

<br>

# 1. Extension Function이란?

Extension Function(확장 함수)은 기존 클래스의 코드를 수정하지 않고 새로운 함수를 추가하는 기능이다.

예를 들어 String 클래스에 함수를 추가할 수 있다.

```kotlin
fun String.printHello() {
    println("Hello")
}
```

사용

```kotlin
"Android".printHello()
```

결과

```text
Hello
```

마치 String 클래스에 원래 존재하던 함수처럼 사용할 수 있다.

<br>

# 2. 왜 사용할까?

기존 클래스를 직접 수정할 수 없는 경우가 많다.

예를 들어 String, Int, List 같은 코틀린 기본 클래스는 소스 코드를 수정할 수 없다.

하지만 프로젝트에서 자주 사용하는 기능은 추가하고 싶을 수 있다.

```kotlin
fun String.isEmail(): Boolean {
    return contains("@")
}
```

사용

```kotlin
println("test@gmail.com".isEmail())
```

결과

```text
true
```

공통 기능을 재사용할 수 있고 코드 가독성도 좋아진다.

<br>

# 3. 기본 사용법

문법

```kotlin
fun 수신객체.함수명() {

}
```

예시

```kotlin
fun String.greet() {
    println("안녕하세요")
}
```

사용

```kotlin
"코틀린".greet()
```

여기서 String을 수신 객체(Receiver)라고 한다.

<br>

# 4. 매개변수 사용하기

확장 함수도 일반 함수처럼 매개변수를 받을 수 있다.

```kotlin
fun String.addPrefix(prefix: String): String {
    return prefix + this
}
```

사용

```kotlin
val result = "World".addPrefix("Hello ")

println(result)
```

결과

```text
Hello World
```

this는 현재 String 객체를 의미한다.

<br>

# 5. 반환값 사용하기

확장 함수는 값을 반환할 수 있다.

```kotlin
fun Int.square(): Int {
    return this * this
}
```

사용

```kotlin
println(5.square())
```

결과

```text
25
```

<br>

# 6. 확장 함수와 클래스 멤버 함수

확장 함수는 실제로 클래스 내부에 추가되는 것이 아니다.

예를 들어

```kotlin
fun String.hello() {
    println("Hello")
}
```

사용

```kotlin
"ABC".hello()
```

컴파일러는 내부적으로 일반 함수 호출 형태로 처리한다.

```kotlin
hello("ABC")
```

와 비슷하게 동작한다고 이해하면 된다.

<br>

만약 클래스 내부에 같은 이름의 함수가 존재한다면 멤버 함수가 우선 호출된다.

```kotlin
class Sample {

    fun hello() {
        println("member")
    }
}

fun Sample.hello() {
    println("extension")
}
```

사용

```kotlin
Sample().hello()
```

결과

```text
member
```

멤버 함수가 항상 우선이다.

<br>

# 7. 실무 예시

## 문자열 유효성 검사

```kotlin
fun String.isNotEmptyTrimmed(): Boolean {
    return trim().isNotEmpty()
}
```

사용

```kotlin
val text = " Android "

println(text.isNotEmptyTrimmed())
```

<br>

## 금액 포맷

```kotlin
fun Int.toPrice(): String {
    return "${this}원"
}
```

사용

```kotlin
println(10000.toPrice())
```

결과

```text
10000원
```

<br>

## List 확장

```kotlin
fun List<Int>.sumAll(): Int {
    return sum()
}
```

사용

```kotlin
val numbers = listOf(1, 2, 3)

println(numbers.sumAll())
```

결과

```text
6
```

<br>

## Android 예시

```kotlin
fun Context.showToast(message: String) {

}
```

사용

```kotlin
context.showToast("완료")
```

안드로이드에서는 Context, View, Modifier 등에 대한 확장 함수를 매우 자주 사용한다.

<br>

# 8. 다른 언어와 차이점

Java에는 Extension Function 문법이 없다.

Java에서는 보통 Util 클래스를 만든다.

```java
public class StringUtil {

    public static boolean isEmail(String text) {
        return text.contains("@");
    }

}
```

사용

```java
StringUtil.isEmail("test@gmail.com");
```

<br>

코틀린은

```kotlin
"test@gmail.com".isEmail()
```

처럼 사용할 수 있어서 훨씬 읽기 쉽다.

<br>

# 9. 주의할 점

확장 함수는 private 멤버에 접근할 수 없다.

```kotlin
class User(
    private val name: String
)
```

아래 코드는 컴파일 에러가 발생한다.

```kotlin
fun User.printName() {
    println(name)
}
```

<br>

확장 함수는 실제로 클래스에 추가되는 것이 아니다.

따라서 상속이나 오버라이드와는 다르게 동작한다.

<br>

또한 너무 많은 확장 함수를 만들면 어떤 함수가 어디에 정의되어 있는지 찾기 어려울 수 있다.

정말 자주 사용하는 기능만 확장 함수로 만드는 것이 좋다.

<br>

# 10. 정리

- Extension Function은 기존 클래스에 함수를 추가하는 기능이다.
- 클래스 소스를 수정하지 않아도 된다.
- String, Int, List 등에 자주 사용한다.
- this는 수신 객체를 의미한다.
- 멤버 함수와 이름이 같으면 멤버 함수가 우선 호출된다.
- private 멤버에는 접근할 수 없다.
- Java의 Util 클래스 역할을 더 자연스럽게 대체할 수 있다.
- Android와 Compose에서 매우 자주 사용된다.

<br>

# 헷갈리기 쉬운 부분

확장 함수를 보면 실제로 클래스에 함수가 추가된 것처럼 보인다.

```kotlin
fun String.isEmail(): Boolean
```

하지만 실제로는 String 클래스가 수정된 것이 아니다.

컴파일러가 일반 함수 호출 형태로 변환해서 처리한다.

그래서

```kotlin
"test@gmail.com".isEmail()
```

은 문법적으로 편하게 사용할 수 있도록 만든 기능이라고 이해하면 된다.

"클래스가 수정된 것"이 아니라 "호출 방법이 편해진 것"이라는 점을 기억하자.
