# Collection Function

컬렉션 함수는 List, Set, Map 등의 데이터를 가공하거나 검색하기 위한 기능이다.

코틀린에서는 단순 반복문보다 컬렉션 함수를 사용하는 경우가 많다.

예를 들어 다음과 같은 코드가 있다고 가정하자.

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)
```

이때

* 데이터를 변환하려면 `map`
* 조건에 맞게 필터링하려면 `filter`
* 값을 찾으려면 `find`
* 그룹으로 묶으려면 `groupBy`

를 사용한다.

<br>

## 목차

1. map
2. filter
3. forEach
4. find
5. first / firstOrNull
6. any / all / none
7. count
8. distinct
9. sortedBy
10. groupBy
11. associateBy
12. map vs forEach
13. find vs first
14. 코틀린에서 특히 많이 사용하는 함수
15. 자주 하는 실수

<br>

# 1. map

각 요소를 다른 형태로 변환한다.

```kotlin
val numbers = listOf(1, 2, 3)

val result = numbers.map {
    it * 10
}

println(result)
```

`[10, 20, 30]`

<br>

객체의 특정 필드만 추출할 때도 자주 사용한다.

```kotlin
val names = users.map {
    it.name
}
```

예를 들어 User 객체 목록을 이름 목록으로 변환할 수 있다.

<br>

# 2. filter

조건에 맞는 데이터만 남긴다.

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)

val result = numbers.filter {
    it % 2 == 0
}

println(result)
```



`[2, 4]`

<br>

실무에서는 서버 응답 목록에서 특정 조건의 데이터만 추릴 때 자주 사용한다.

<br>

# 3. forEach

모든 요소를 순회한다.

```kotlin
numbers.forEach {
    println(it)
}
```

<br>

주의할 점은 데이터를 변환하지 않는다는 것이다.

```kotlin
numbers.forEach {
    it * 10
}
```

위 코드는 계산만 할 뿐 를 만들지 않는다.

`forEach`는 순회용이다.

<br>

# 4. find

조건을 만족하는 첫 번째 요소를 찾는다.

```kotlin
val result = numbers.find {
    it > 3
}
```



`4`

<br>

조건을 만족하는 값이 없으면

`null`

을 반환한다.

```kotlin
val result = numbers.find {
    it > 100
}
```



`null`

<br>

# 5. first / firstOrNull

첫 번째 요소를 반환한다.

```kotlin
val result = numbers.first()
```



`1`

<br>

조건을 사용할 수도 있다.

```kotlin
val result = numbers.first {
    it > 3
}
```



`4`

<br>

### first

```kotlin
numbers.first {
    it > 100
}
```

조건을 만족하는 값이 없으면 예외가 발생한다.

<br>

### firstOrNull

```kotlin
numbers.firstOrNull {
    it > 100
}
```



`null`

실무에서는 `firstOrNull`을 훨씬 많이 사용한다.

<br>

# 6. any / all / none

조건을 검사하는 함수들이다.

<br>

### any

하나라도 만족하면 true

```kotlin
val result = numbers.any {
    it > 3
}
```



`true`

<br>

### all

모두 만족해야 true

```kotlin
val result = numbers.all {
    it > 0
}
```



`true`

<br>

### none

아무도 만족하지 않아야 true

```kotlin
val result = numbers.none {
    it < 0
}
```



`true`

<br>

실무에서는 리스트가 비어있는지 확인하거나 특정 데이터 존재 여부를 확인할 때 자주 사용한다.

<br>

# 7. count

조건을 만족하는 데이터 개수를 센다.

```kotlin
val result = numbers.count {
    it % 2 == 0
}
```



`2`

<br>

# 8. distinct

중복을 제거한다.

```kotlin
val numbers = listOf(
    1,
    1,
    2,
    2,
    3
)

println(numbers.distinct())
```



`[1, 2, 3]`

<br>

Set으로 변환하지 않고 중복 제거만 하고 싶을 때 사용한다.

<br>

# 9. sortedBy

특정 기준으로 정렬한다.

```kotlin
val users = listOf(
    User("Kim", 30),
    User("Lee", 20),
    User("Park", 25)
)

val result = users.sortedBy {
    it.age
}
```

나이 기준 오름차순 정렬

<br>

내림차순은

```kotlin
users.sortedByDescending {
    it.age
}
```

를 사용한다.

<br>

# 10. groupBy

같은 기준끼리 데이터를 묶는다.

```kotlin
val users = listOf(
    User("Kim", "A"),
    User("Lee", "A"),
    User("Park", "B")
)

val result = users.groupBy {
    it.team
}
```



```text
A -> [Kim, Lee]
B -> [Park]
```

<br>

실무에서 생각보다 자주 사용한다.

특히 카테고리별, 날짜별, 상태별 그룹핑에 유용하다.

<br>

# 11. associateBy

특정 값을 Key로 하는 Map을 만든다.

```kotlin
val result = users.associateBy {
    it.id
}
```

 타입

```kotlin
Map<Int, User>
```

<br>

이후에는

```kotlin
result[1]
```

처럼 빠르게 조회할 수 있다.

<br>

groupBy보다 덜 알려져 있지만 실무에서는 매우 많이 사용된다.

<br>

# 12. map vs forEach

컬렉션 함수에서 가장 많이 헷갈리는 부분이다.

<br>

### map

변환

```kotlin
val result = numbers.map {
    it * 10
}
```



`[10, 20, 30]`

<br>

### forEach

순회

```kotlin
numbers.forEach {
    println(it)
}
```

<br>

기준은 간단하다.

* 가 필요하면 `map`
* 순회만 하면 `forEach`

<br>

# 13. find vs first

둘 다 값을 찾는 함수지만 동작이 다르다.

<br>

### find

```kotlin
numbers.find {
    it > 100
}
```



`null`

<br>

### first

```kotlin
numbers.first {
    it > 100
}
```

예외 발생

<br>

안전하게 사용하려면

```kotlin
firstOrNull()
```

또는

```kotlin
find()
```

를 사용하는 것이 좋다.

<br>

# 14. 코틀린에서 특히 많이 사용하는 함수

실무 체감 기준으로 가장 자주 보이는 함수들이다.

★★★★★ map

★★★★★ filter

★★★★★ firstOrNull

★★★★★ any

★★★★☆ groupBy

★★★★☆ associateBy

★★★☆☆ count

★★★☆☆ distinct

★★☆☆☆ sortedBy

<br>

특히

```kotlin
filter
map
firstOrNull
```

이 세 개는 거의 매일 사용한다고 생각해도 된다.

<br>

# 15. 자주 하는 실수

### 1) map 를 사용하지 않음

```kotlin
numbers.map {
    it * 10
}
```

변환 가 버려진다.

```kotlin
val result = numbers.map {
    it * 10
}
```

처럼 를 저장하거나 바로 사용해야 한다.

<br>

### 2) first 사용

```kotlin
numbers.first {
    it > 100
}
```

조건을 만족하는 값이 없으면 예외가 발생한다.

가능하면

```kotlin
numbers.firstOrNull {
    it > 100
}
```

을 사용하는 것이 안전하다.

<br>

### 3) forEach로 데이터 변환

```kotlin
val result = mutableListOf<Int>()

numbers.forEach {
    result.add(it * 10)
}
```

가능은 하지만

```kotlin
val result = numbers.map {
    it * 10
}
```

가 더 코틀린스럽고 가독성도 좋다.

<br>

### 4) filter와 find 차이를 헷갈림

```kotlin
numbers.filter {
    it > 3
}
```

`[4, 5]`

<br>

```kotlin
numbers.find {
    it > 3
}
```



`4`

<br>

* `filter`는 여러 개 반환
* `find`는 첫 번째 하나만 반환

이 차이를 기억하자.
