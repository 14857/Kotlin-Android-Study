# Map

Map은 **Key와 Value를 한 쌍으로 저장하는 컬렉션**이다.

List는 순서로 데이터를 관리하고, Set은 중복 없이 데이터를 관리하지만,

Map은 Key를 이용해 Value를 빠르게 찾을 수 있다.

<br>

## 목차

1. Map이란?
2. mapOf
3. Key와 Value 조회
4. containsKey / containsValue
5. keys / values
6. MutableMap
7. put
8. remove
9. getOrDefault
10. 실무 예제
11. 자주 하는 실수

<br>

# 1. Map이란?

Map은 Key와 Value를 한 쌍으로 저장한다.

```kotlin
val scores = mapOf(
    "Kim" to 95,
    "Lee" to 88,
    "Park" to 100
)
```

구조

```text
Key     Value
Kim     95
Lee     88
Park    100
```

<br>

# 2. mapOf

읽기 전용 Map을 생성한다.

```kotlin
val scores = mapOf(
    "Kim" to 95,
    "Lee" to 88,
    "Park" to 100
)

println(scores)
```

결과

```text
{Kim=95, Lee=88, Park=100}
```

<br>

Key는 중복될 수 없다.

```kotlin
val scores = mapOf(
    "Kim" to 95,
    "Kim" to 80
)

println(scores)
```

결과

```text
{Kim=80}
```

마지막 값이 저장된다.

<br>

# 3. Key와 Value 조회

대괄호를 사용하여 조회할 수 있다.

```kotlin
val scores = mapOf(
    "Kim" to 95,
    "Lee" to 88
)

println(scores["Kim"])
```

결과

```text
95
```

<br>

get()을 사용할 수도 있다.

```kotlin
println(scores.get("Lee"))
```

결과

```text
88
```

<br>

존재하지 않는 Key를 조회하면

```kotlin
println(scores["Park"])
```

결과

```text
null
```

<br>

# 4. containsKey / containsValue

Key 존재 여부 확인

```kotlin
val scores = mapOf(
    "Kim" to 95,
    "Lee" to 88
)

println(scores.containsKey("Kim"))
```

결과

```text
true
```

<br>

Value 존재 여부 확인

```kotlin
println(scores.containsValue(88))
```

결과

```text
true
```

<br>

# 5. keys / values

모든 Key 조회

```kotlin
val scores = mapOf(
    "Kim" to 95,
    "Lee" to 88
)

println(scores.keys)
```

결과

```text
[Kim, Lee]
```

<br>

모든 Value 조회

```kotlin
println(scores.values)
```

결과

```text
[95, 88]
```

<br>

# 6. MutableMap

수정 가능한 Map이다.

```kotlin
val scores = mutableMapOf(
    "Kim" to 95,
    "Lee" to 88
)
```

<br>

읽기 전용 Map과 비교

```kotlin
val readOnlyMap = mapOf(
    "Kim" to 95
)

val mutableMap = mutableMapOf(
    "Kim" to 95
)
```

```kotlin
mutableMap["Park"] = 100
```

가능

```kotlin
readOnlyMap["Park"] = 100
```

불가능

<br>

# 7. put

데이터 추가

```kotlin
val scores = mutableMapOf(
    "Kim" to 95
)

scores.put("Lee", 88)

println(scores)
```

결과

```text
{Kim=95, Lee=88}
```

<br>

대괄호 문법도 가능하다.

```kotlin
scores["Park"] = 100
```

<br>

기존 Key가 있으면 값이 변경된다.

```kotlin
scores["Kim"] = 80

println(scores)
```

결과

```text
{Kim=80, Lee=88, Park=100}
```

<br>

# 8. remove

데이터 삭제

```kotlin
val scores = mutableMapOf(
    "Kim" to 95,
    "Lee" to 88
)

scores.remove("Kim")

println(scores)
```

결과

```text
{Lee=88}
```

<br>

# 9. getOrDefault

Key가 없을 때 기본값 반환

```kotlin
val scores = mapOf(
    "Kim" to 95
)

println(
    scores.getOrDefault("Park", 0)
)
```

결과

```text
0
```

<br>

# 10. 실무 예제

언어 코드와 언어명 관리

```kotlin
val languageMap = mapOf(
    "ko" to "Korean",
    "en" to "English",
    "ja" to "Japanese"
)

println(languageMap["ko"])
```

결과

```text
Korean
```

<br>

사용자별 포인트 저장

```kotlin
val pointMap = mutableMapOf(
    1 to 1200,
    2 to 800,
    3 to 1500
)

pointMap[2] = 1000

println(pointMap)
```

결과

```text
{1=1200, 2=1000, 3=1500}
```

<br>

# 11. 자주 하는 실수

### 1) mapOf를 수정하려고 하는 경우

```kotlin
val scores = mapOf(
    "Kim" to 95
)

scores["Lee"] = 88
```

컴파일 에러

수정이 필요하면 mutableMapOf를 사용해야 한다.

<br>

### 2) 존재하지 않는 Key 조회

```kotlin
val scores = mapOf(
    "Kim" to 95
)

println(scores["Park"])
```

결과

```text
null
```

Map 조회 결과는 nullable일 수 있다는 점을 항상 기억하자.

<br>

### 3) Key 중복

```kotlin
val scores = mapOf(
    "Kim" to 95,
    "Kim" to 80
)
```

결과

```text
{Kim=80}
```

중복 Key는 마지막 값으로 덮어써진다.
