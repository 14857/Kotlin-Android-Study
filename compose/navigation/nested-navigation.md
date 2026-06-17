# Nested Navigation

## 목차

1. Nested Navigation이란?
2. 왜 사용하는가?
3. Nested Navigation 없이 구현하면?
4. 기본 구조
5. Nested Navigation 만들기
6. Auth Graph 예제
7. Main Graph 예제
8. 화면 이동하기
9. Graph 단위로 이동하기
10. 실무에서 자주 사용하는 구조
11. Nested Navigation 사용 시 주의사항
12. 정리

# 1. Nested Navigation이란?

Nested Navigation은 Navigation Graph 안에 또 다른 Navigation Graph를 만드는 기능이다.

예를 들어 앱에 다음과 같은 화면이 있다고 가정해보자.

```text
Login
Signup
Home
Profile
Setting
```

모든 화면을 하나의 NavHost에 넣을 수도 있지만, 화면이 많아질수록 관리가 어려워진다.

그래서 관련된 화면끼리 그룹으로 묶는다.

```text
Auth Graph
 ├ Login
 └ Signup

Main Graph
 ├ Home
 ├ Profile
 └ Setting
```

이렇게 Navigation Graph 안에 Navigation Graph를 만드는 것을 Nested Navigation이라고 한다.

<br>

# 2. 왜 사용하는가?

앱이 커질수록 화면 수가 많아진다.

예를 들어

```text
Login
Signup
FindPassword
Home
Profile
Setting
Notification
Chat
Detail
```

모든 화면을 하나의 NavHost에 넣으면

```kotlin
NavHost(
    navController = navController,
    startDestination = "login"
) {
    composable("login") {}
    composable("signup") {}
    composable("findPassword") {}
    composable("home") {}
    composable("profile") {}
    composable("setting") {}
    composable("notification") {}
    composable("chat") {}
    composable("detail") {}
}
```

화면이 계속 늘어난다.

그러면

* 관리하기 어렵다.
* 어떤 화면이 같은 그룹인지 알기 어렵다.
* 유지보수가 힘들다.

그래서 기능별로 Navigation Graph를 분리한다.

<br>

# 3. Nested Navigation 없이 구현하면?

예를 들어 로그인 후 Home으로 이동해야 한다고 가정해보자.

```kotlin
navController.navigate("home")
```

문제는 앱이 커지면

```text
login
signup
home
profile
setting
chat
detail
```

모든 화면 경로를 알아야 한다.

하지만 Nested Navigation을 사용하면

```text
Auth Graph
Main Graph
```

처럼 큰 단위로 나눌 수 있다.

구조를 이해하기 쉬워진다.

<br>

# 4. 기본 구조

```kotlin
NavHost(
    navController = navController,
    startDestination = "auth"
) {

    navigation(
        route = "auth",
        startDestination = "login"
    ) {

        composable("login") {}

        composable("signup") {}
    }

    navigation(
        route = "main",
        startDestination = "home"
    ) {

        composable("home") {}

        composable("profile") {}
    }
}
```

여기서

```kotlin
navigation(
    route = "auth",
    startDestination = "login"
)
```

는

```text
auth 라는 그래프 생성
 ├ login
 └ signup
```

을 의미한다.

<br>

# 5. Nested Navigation 만들기

가장 많이 사용하는 구조이다.

```text
Auth Graph
 ├ Login
 └ Signup

Main Graph
 ├ Home
 ├ Profile
 └ Setting
```

Compose에서는

```kotlin
NavHost(
    navController = navController,
    startDestination = "auth"
)
```

처럼 시작 그래프를 지정한다.

<br>

# 6. Auth Graph 예제

```kotlin
navigation(
    route = "auth",
    startDestination = "login"
) {

    composable("login") {
        LoginScreen()
    }

    composable("signup") {
        SignupScreen()
    }
}
```

구조

```text
auth
 ├ login
 └ signup
```

사용자가 앱을 처음 실행하면

```text
login
```

화면이 표시된다.

왜냐하면

```kotlin
startDestination = "login"
```

으로 설정했기 때문이다.

<br>

# 7. Main Graph 예제

```kotlin
navigation(
    route = "main",
    startDestination = "home"
) {

    composable("home") {
        HomeScreen()
    }

    composable("profile") {
        ProfileScreen()
    }

    composable("setting") {
        SettingScreen()
    }
}
```

구조

```text
main
 ├ home
 ├ profile
 └ setting
```

Home, Profile, Setting이 하나의 그룹이 된다.

<br>

# 8. 화면 이동하기

로그인 성공 시

```kotlin
navController.navigate("home")
```

가능하지만

실무에서는 보통 Graph 단위로 이동한다.

```kotlin
navController.navigate("main")
```

그러면

```text
main
 └ startDestination(home)
```

으로 이동한다.

즉

```kotlin
startDestination = "home"
```

이 자동으로 실행된다.

이게 Nested Navigation의 가장 큰 장점이다.

<br>

# 9. Graph 단위로 이동하기

로그인 성공 후 Main Graph로 이동

```kotlin
Button(
    onClick = {
        navController.navigate("main")
    }
) {
    Text("로그인")
}
```

실제로는

```text
main
 └ home
```

으로 이동한다.

왜냐하면

```kotlin
route = "main"
```

은 Graph의 이름이기 때문이다.

<br>

# 10. 실무에서 자주 사용하는 구조

대부분 앱은 아래 형태로 구성된다.

```text
Root Graph
│
├ Auth Graph
│  ├ Login
│  ├ Signup
│  └ FindPassword
│
├ Main Graph
│  ├ Home
│  ├ Search
│  ├ Profile
│  └ Setting
│
└ Detail Graph
   ├ ProductDetail
   └ Review
```

예시

```kotlin
NavHost(
    navController = navController,
    startDestination = "auth"
) {

    authGraph(navController)

    mainGraph(navController)

    detailGraph(navController)
}
```

실무에서는 Graph를 함수로 분리한다.

<br>

# 11. Nested Navigation 사용 시 주의사항

## 1) route 이름이 겹치면 안 된다

```kotlin
navigation(
    route = "main",
    startDestination = "home"
)
```

```kotlin
navigation(
    route = "main",
    startDestination = "profile"
)
```

같은 route 사용 불가

<br>

## 2) Graph route와 Screen route를 구분하기

좋은 예

```kotlin
auth
login
signup

main
home
profile
```

나쁜 예

```kotlin
main
main
```

Graph와 화면 이름을 동일하게 사용하면 헷갈린다.

<br>

## 3) 화면이 아닌 Graph로 이동 가능

```kotlin
navController.navigate("main")
```

가능

```kotlin
navController.navigate("auth")
```

가능

이 경우 해당 Graph의 startDestination으로 이동한다.

<br>

# 12. 정리

Nested Navigation은 관련된 화면들을 하나의 Navigation Graph로 묶는 기능이다.

장점

* 화면 구조를 이해하기 쉽다.
* 기능별로 분리할 수 있다.
* 유지보수가 쉬워진다.
* Graph 단위 이동이 가능하다.
* 실무에서 거의 필수로 사용된다.

실무에서 가장 흔한 구조

```text
Root Graph
│
├ Auth Graph
│  ├ Login
│  └ Signup
│
└ Main Graph
   ├ Home
   ├ Profile
   └ Setting
```

Nested Navigation의 핵심은

```kotlin
navigation(
    route = "main",
    startDestination = "home"
)
```

이 코드가

```text
main 그래프 생성
 ├ home
 ├ profile
 └ setting
```

이라는 의미라는 것을 이해하는 것이다.

그리고

```kotlin
navController.navigate("main")
```

을 호출하면

```text
home
```

으로 이동하는 이유는

```kotlin
startDestination = "home"
```

이 자동으로 실행되기 때문이다.
