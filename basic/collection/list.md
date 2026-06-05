# List

## 목차

1. List란?
2. List 생성
3. 요소 접근
4. List와 MutableList
5. MutableList
6. List 순회
7. 주요 함수
8. List 사용 예제
9. 정리

<br>

## 1. List란?

List는 순서가 있는 컬렉션이다.

특징

* 요소의 순서가 유지된다.
* 같은 값을 여러 번 저장할 수 있다.
* 인덱스를 통해 요소에 접근할 수 있다.

```kotlin
val fruits = listOf("Apple", "Banana", "Orange")
```

결과

```text
[Apple, Banana, Orange]
```

<br>

## 2. List 생성

### 읽기 전용 List

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)
```

### 빈 List 생성

```kotlin
val emptyList = emptyList<Int>()
```

<br>

## 3. 요소 접근

인덱스를 사용하여 요소를 조회할 수 있다.

```kotlin
val fruits = listOf("Apple", "Banana", "Orange")

println(fruits[0])
println(fruits[1])
```

결과

```text
Apple
Banana
```

### first()

첫 번째 요소를 반환한다.

```kotlin
println(fruits.first())
```

결과

```text
Apple
```

### last()

마지막 요소를 반환한다.

```kotlin
println(fruits.last())
```

결과

```text
Orange
```

<br>

## 4. List와 MutableList

코틀린의 List는 읽기 전용(Read-Only) 컬렉션이다.

```kotlin
val numbers = listOf(1, 2, 3)
```

List는 요소 조회만 가능하며 추가, 수정, 삭제는 할 수 없다.

```kotlin
numbers.add(4)      // 오류
numbers.remove(1)   // 오류
numbers[0] = 100    // 오류
```

### 크기 고정 컬렉션인가?

엄밀히 말하면 크기가 고정된 컬렉션이라기보다 읽기 전용 컬렉션이다.

기존 List를 수정할 수는 없지만 새로운 List를 만들어 다시 할당할 수는 있다.

```kotlin
var numbers = listOf(1, 2, 3)

numbers = listOf(1, 2, 3, 4)
```

위 코드는 기존 List를 수정한 것이 아니라 새로운 List를 생성하여 변수에 다시 할당한 것이다.

### val과 MutableList

많이 헷갈리는 부분 중 하나이다.

```kotlin
val numbers = mutableListOf(1, 2, 3)

numbers.add(4)
numbers[0] = 100
```

위 코드는 정상 동작한다.

이유는 val이 컬렉션의 내부 데이터 변경을 막는 것이 아니라 변수의 참조 변경만 막기 때문이다.

```text
val
→ 변수 재할당 불가

MutableList
→ 내부 요소 변경 가능
```

따라서 아래 코드는 오류가 발생한다.

```kotlin
val numbers = mutableListOf(1, 2, 3)

numbers = mutableListOf(4, 5, 6) // 오류
```

### List와 MutableList 비교

| 구분    | List | MutableList |
| ----- | ---- | ----------- |
| 요소 조회 | O    | O           |
| 요소 수정 | X    | O           |
| 요소 추가 | X    | O           |
| 요소 삭제 | X    | O           |

<br>

## 5. MutableList

MutableList는 요소 추가, 수정, 삭제가 가능하다.

```kotlin
val numbers = mutableListOf(1, 2, 3)

numbers.add(4)

println(numbers)
```

결과

```text
[1, 2, 3, 4]
```

### 요소 수정

```kotlin
numbers[0] = 100

println(numbers)
```

결과

```text
[100, 2, 3, 4]
```

### 요소 삭제

```kotlin
numbers.remove(2)

println(numbers)
```

결과

```text
[100, 3, 4]
```

<br>

## 6. List 순회

### for 문

```kotlin
val fruits = listOf("Apple", "Banana", "Orange")

for (fruit in fruits) {
    println(fruit)
}
```

### 인덱스 사용

```kotlin
for (i in fruits.indices) {
    println("${i}: ${fruits[i]}")
}
```

결과

```text
0: Apple
1: Banana
2: Orange
```

<br>

## 7. 주요 함수

### contains()

```kotlin
val fruits = listOf("Apple", "Banana")

println(fruits.contains("Apple"))
```

결과

```text
true
```

### size

```kotlin
println(fruits.size)
```

결과

```text
2
```

### isEmpty()

```kotlin
println(fruits.isEmpty())
```

결과

```text
false
```

<br>

## 8. List 사용 예제

```kotlin
val students = mutableListOf("Kim", "Lee")

students.add("Park")

for (student in students) {
    println(student)
}
```

결과

```text
Kim
Lee
Park
```

<br>

## 9. 정리

* List는 순서가 있는 컬렉션이다.
* 중복 데이터를 허용한다.
* 인덱스로 요소를 조회할 수 있다.
* List는 읽기 전용 컬렉션이다.
* MutableList는 추가, 수정, 삭제가 가능하다.
* val은 재할당을 막을 뿐 내부 요소 변경까지 막지는 않는다.
