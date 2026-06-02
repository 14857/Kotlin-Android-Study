# 제어문 (Control Flow)

<br>

## 목차

1. if 표현식
2. when 표현식
3. for 반복문
4. while 반복문
5. break / continue
6. Label

<br>

## 1. if 표현식

코틀린의 `if`는 문(Statement)이면서 표현식(Expression)이다.

다른 언어의 삼항 연산자 역할까지 수행할 수 있다.

```kotlin
val max = if (a > b) a else b
```

Java의 삼항 연산자:

```java
int max = a > b ? a : b;
```

블록을 사용하면 마지막 표현식이 반환된다.

```kotlin
val result = if (score >= 60) {
    "Pass"
} else {
    "Fail"
}
```

<br>

### 특징

* 삼항 연산자(`?:`)가 없다.
* `if` 자체가 값을 반환할 수 있다.
* 변수 초기화에 자주 사용된다.

<br>
<br>

## 2. when 표현식

`when`은 Java의 `switch`보다 확장된 기능을 제공한다.

```kotlin
when (day) {
    1 -> println("월요일")
    2 -> println("화요일")
    else -> println("기타")
}
```

값을 반환할 수도 있다.

```kotlin
val grade = when (score) {
    in 90..100 -> "A"
    in 80..89 -> "B"
    else -> "F"
}
```

<br>

### 여러 조건 처리

```kotlin
when (x) {
    1, 2, 3 -> println("작은 수")
    4, 5, 6 -> println("큰 수")
}
```

<br>

### 범위 사용

```kotlin
when (score) {
    in 90..100 -> println("A")
    in 80..89 -> println("B")
    else -> println("F")
}
```

<br>

### 타입 검사

```kotlin
when (obj) {
    is String -> println(obj.length)
    is Int -> println(obj * 2)
}
```

`is`를 사용하면 타입 검사 후 Smart Cast가 적용된다.

```kotlin
if (obj is String) {
    println(obj.length)
}
```

별도의 캐스팅 없이 사용할 수 있다.

<br>

### 인자 없는 when

```kotlin
when {
    score >= 90 -> println("A")
    score >= 80 -> println("B")
    else -> println("F")
}
```

<br>

### 특징

* 표현식으로 사용 가능
* 범위 검사 가능
* 타입 검사 가능
* Smart Cast 지원
* Java `switch`보다 활용 범위가 넓다

<br>
<br>

## 3. for 반복문

코틀린은 전통적인 C 스타일 for 문을 지원하지 않는다.

```java
for (int i = 0; i < 10; i++)
```

대신 범위(Range)를 활용한다.

```kotlin
for (i in 0..9) {
    println(i)
}
```

<br>

### until

마지막 값을 포함하지 않는다.

```kotlin
for (i in 0 until 10) {
    println(i)
}
```

출력

```text
0 ~ 9
```

<br>

### step

증감값 지정

```kotlin
for (i in 0..10 step 2) {
    println(i)
}
```

출력

```text
0
2
4
6
8
10
```

<br>

### downTo

역순 순회

```kotlin
for (i in 10 downTo 1) {
    println(i)
}
```

출력

```text
10
9
8
...
1
```

<br>

### 컬렉션 순회

```kotlin
val list = listOf("A", "B", "C")

for (item in list) {
    println(item)
}
```

<br>

### 인덱스와 함께 순회

```kotlin
for ((index, value) in list.withIndex()) {
    println("$index : $value")
}
```

<br>

## 4. while 반복문

동작은 대부분의 언어와 동일하다.

```kotlin
var count = 0

while (count < 5) {
    println(count)
    count++
}
```

<br>

### do-while

조건과 관계없이 최소 한 번 실행된다.

```kotlin
do {
    println("실행")
} while (false)
```

<br>
<br>

## 5. break / continue

### break

반복문을 종료한다.

```kotlin
for (i in 1..10) {
    if (i == 5) break
    println(i)
}
```

출력

```text
1
2
3
4
```

<br>

### continue

현재 반복만 건너뛴다.

```kotlin
for (i in 1..5) {
    if (i == 3) continue
    println(i)
}
```

출력

```text
1
2
4
5
```

<br>
<br>

## 6. Label

코틀린은 Label을 지원한다.

중첩 반복문에서 특정 반복문을 제어할 수 있다.

```kotlin
// outer@ : for문 이름을 outer로 지정한다는 의미
outer@ for (i in 1..3) {
    for (j in 1..3) {
        if (j == 2) break@outer
        println("$i $j")
    }
}
```

<br>

### continue와 함께 사용

```kotlin
outer@ for (i in 1..3) {
    for (j in 1..3) {
        if (j == 2) continue@outer
        println("$i $j")
    }
}
```

<br>

### 람다와 함께 사용

```kotlin
listOf(1, 2, 3, 4).forEach {
    if (it == 3) return@forEach
    println(it)
}
```

람다 내부에서 현재 반복만 건너뛸 수 있다.

<br>

## 정리

| 기능     | 코틀린 특징             |
| ------ | ------------------ |
| if     | 표현식으로 사용 가능        |
| when   | switch보다 강력        |
| is     | 타입 검사 + Smart Cast |
| for    | 범위 기반 반복           |
| until  | 마지막 값 제외           |
| downTo | 역순 반복              |
| step   | 증감값 지정             |
| Label  | 특정 반복문 제어 가능       |

<br>

## 참고

* Kotlin 공식 문서: Control Flow
* Kotlin 공식 문서: Loops
* Kotlin 공식 문서: When Expressions
