# Navigation Argument

## 목차

1. Navigation Argument란?
2. Path Parameter 전달
3. Argument 받기
4. Query Parameter 전달
5. Nullable Argument
6. 기본값(Default Value)
7. Argument 타입 지정
8. 여러 개 Argument 전달
9. 자주 하는 실수
10. Android Intent와 비교
11. 정리

# 1. Navigation Argument란?

Navigation Argument는 화면 이동 시 데이터를 함께 전달하는 방법이다.

예를 들어 다음과 같은 경우 사용한다.

* 게시글 목록 → 게시글 상세
* 상품 목록 → 상품 상세
* 사용자 목록 → 사용자 프로필
* 채팅방 목록 → 채팅방 상세

상세 화면은 어떤 데이터를 보여줄지 알아야 하므로 ID와 같은 값을 전달해야 한다.

예시

```kotlin
navController.navigate("detail/10")
```

위 코드는 id가 10인 상세 화면으로 이동한다.

<br>

# 2. Path Parameter 전달

가장 많이 사용하는 방식이다.

목록 화면

```kotlin
navController.navigate("detail/10")
```

NavHost

```kotlin
composable(
    route = "detail/{id}"
) {
    DetailScreen()
}
```

여기서

```text
{id}
```

부분이 전달받을 값의 위치를 의미한다.

웹 URL과 매우 비슷한 방식이다.

```text
detail/10
detail/20
detail/30
```

실무에서는 대부분 상세 화면 이동 시 이 방식을 사용한다.

<br>

# 3. Argument 받기

전달받은 값은 backStackEntry에서 가져올 수 있다.

```kotlin
composable(
    route = "detail/{id}"
) { backStackEntry ->

    val id =
        backStackEntry.arguments
            ?.getString("id")

    Text(text = id ?: "")
}
```

결과

```text
10
```

<br>

왜 String으로 받을까?

Navigation Route는 내부적으로 문자열(String) 기반으로 동작하기 때문이다.

그래서 숫자를 전달하더라도 Route 자체는 문자열 형태로 관리된다.

<br>

# 4. Query Parameter 전달

선택적인 값을 전달할 때 사용한다.

예시

```kotlin
navController.navigate(
    "search?keyword=compose"
)
```

Route

```kotlin
composable(
    route = "search?keyword={keyword}"
) {

}
```

URL 형태로 보면 다음과 같다.

```text
search?keyword=compose
```

웹 개발 경험이 있다면 익숙한 방식이다.

<br>

Path Parameter

```text
detail/10
```

Query Parameter

```text
search?keyword=compose
```

보통

* 필수 값 → Path Parameter
* 선택 값 → Query Parameter

형태로 사용한다.

<br>

# 5. Nullable Argument

값이 없어도 되는 경우가 있다.

예시

```kotlin
navController.navigate(
    "search"
)
```

또는

```kotlin
navController.navigate(
    "search?keyword=compose"
)
```

Route

```kotlin
composable(
    route = "search?keyword={keyword}",
    arguments = listOf(
        navArgument("keyword") {
            nullable = true
        }
    )
) {

}
```

값 꺼내기

```kotlin
val keyword =
    backStackEntry.arguments
        ?.getString("keyword")
```

결과

```text
null
```

가능

<br>

Nullable을 사용하면 null 체크가 계속 필요하다.

```kotlin
keyword?.length
```

따라서 실무에서는 기본값을 주는 경우가 더 많다.

<br>

# 6. 기본값(Default Value)

Argument가 없을 때 기본값을 지정할 수 있다.

```kotlin
composable(
    route = "search?keyword={keyword}",
    arguments = listOf(
        navArgument("keyword") {
            defaultValue = ""
        }
    )
) {

}
```

값을 전달하지 않아도

```text
""
```

가 자동으로 사용된다.

<br>

실무에서는 다음과 같이 자주 사용한다.

```kotlin
defaultValue = ""
```

```kotlin
defaultValue = 0
```

```kotlin
defaultValue = false
```

Nullable보다 코드가 단순해지는 장점이 있다.

<br>

# 7. Argument 타입 지정

Navigation은 Argument 타입도 지정할 수 있다.

```kotlin
composable(
    route = "detail/{id}",
    arguments = listOf(
        navArgument("id") {
            type = NavType.IntType
        }
    )
) {

}
```

값 가져오기

```kotlin
val id =
    backStackEntry.arguments
        ?.getInt("id")
```

<br>

주요 타입

```kotlin
NavType.IntType
```

```kotlin
NavType.StringType
```

```kotlin
NavType.BoolType
```

```kotlin
NavType.FloatType
```

타입을 지정하면 잘못된 데이터 전달을 어느 정도 방지할 수 있다.

<br>

# 8. 여러 개 Argument 전달

예시

```kotlin
navController.navigate(
    "detail/10/comment/3"
)
```

Route

```kotlin
composable(
    route = "detail/{postId}/comment/{commentId}"
) { backStackEntry ->

    val postId =
        backStackEntry.arguments
            ?.getString("postId")

    val commentId =
        backStackEntry.arguments
            ?.getString("commentId")
}
```

결과

```text
postId = 10
commentId = 3
```

<br>

Query Parameter도 여러 개 전달 가능하다.

```kotlin
navController.navigate(
    "search?keyword=compose&page=1"
)
```

<br>

실무 예시

```text
product/10
```

```text
chat/123
```

```text
profile/55
```

대부분 ID 하나만 전달하는 경우가 많다.

<br>

# 9. 자주 하는 실수

## Route 이름 불일치

잘못된 예

```kotlin
navController.navigate(
    "detail/10"
)
```

```kotlin
composable(
    route = "detail/{itemId}"
)
```

```kotlin
arguments?.getString("id")
```

결과

```text
null
```

발생

<br>

이름은 반드시 동일해야 한다.

```kotlin
{id}
```

↓

```kotlin
getString("id")
```

<br>

## 객체 전체 전달

초보자가 자주 하는 실수다.

```kotlin
navController.navigate(
    "detail/$user"
)
```

객체 전체를 Route에 넣는 것은 권장되지 않는다.

<br>

실무에서는 보통

```kotlin
navController.navigate(
    "detail/${user.id}"
)
```

처럼 ID만 전달한다.

필요한 데이터는 ViewModel이나 Repository에서 다시 조회한다.

<br>

## 특수문자 포함 문자열 전달

잘못된 예

```kotlin
val keyword =
    "Jetpack Compose"
```

```kotlin
navController.navigate(
    "search?keyword=$keyword"
)
```

공백, ?, &, / 같은 특수문자가 포함되면 Route 파싱 문제가 발생할 수 있다.

이 경우 URL 인코딩을 고려해야 한다.

<br>

## Parcelable 전달에 집착하기

Compose Navigation을 처음 배울 때 자주 하는 실수다.

Activity에서는 Parcelable을 자주 사용했지만 Compose Navigation에서는 보통 ID만 전달한다.

```kotlin
detail/10
```

이후 ViewModel에서 데이터를 조회하는 방식이 훨씬 일반적이다.

<br>

# 10. Android Intent와 비교

기존 Activity

```kotlin
val intent =
    Intent(this, DetailActivity::class.java)

intent.putExtra(
    "id",
    10
)
```

받기

```kotlin
intent.getIntExtra(
    "id",
    0
)
```

<br>

Compose Navigation

```kotlin
navController.navigate(
    "detail/10"
)
```

받기

```kotlin
arguments?.getInt("id")
```

<br>

차이점

| Intent       | Navigation    |
| ------------ | ------------- |
| Key-Value 전달 | Route 기반 전달   |
| Activity 이동  | Composable 이동 |
| putExtra 사용  | navigate 사용   |
| getExtra 사용  | arguments 사용  |

<br>

# 11. 정리

* Navigation Argument는 화면 이동 시 데이터를 전달하기 위한 기능이다.
* 가장 많이 사용하는 방식은 Path Parameter이다.
* 선택적인 값은 Query Parameter를 사용한다.
* Nullable보다 Default Value를 사용하는 경우가 많다.
* Argument 타입을 지정할 수 있다.
* Argument 이름은 Route와 동일해야 한다.
* 객체 전체보다 ID만 전달하는 것이 일반적이다.
* 실무에서는 화면 이동 후 ViewModel에서 데이터를 조회하는 패턴을 가장 많이 사용한다.
* Android Intent의 putExtra와 비슷한 역할을 한다.
