# Compose Navigation

## Navigation이란?

Navigation은 앱에서 화면 간 이동을 관리하는 라이브러리이다.

Jetpack Compose에서는 Navigation Compose 라이브러리를 사용하여 화면 이동을 구현한다.

기존 Android의 Activity 전환 방식보다 단순하고, 하나의 Activity 안에서 여러 Composable 화면을 관리할 수 있다.

<br>

## Navigation이 필요한 이유

예를 들어 앱에 다음과 같은 화면이 있다고 가정한다.

```text
Home Screen
     ↓
Detail Screen
     ↓
Setting Screen
```

사용자가 화면을 이동할 때 현재 화면을 제거하고 새로운 화면을 표시해야 한다.

Navigation은 이러한 화면 전환을 쉽게 관리할 수 있도록 도와준다.

<br>

## 의존성 추가

```kotlin
implementation("androidx.navigation:navigation-compose:2.9.0")
```

최신 버전은 Android Developers 문서를 참고한다.

<br>

## 주요 구성 요소

| 구성 요소         | 설명                  |
| ------------- | ------------------- |
| NavController | 화면 이동 관리            |
| NavHost       | 화면들을 담는 컨테이너        |
| composable    | 목적지(Destination) 정의 |
| route         | 화면 식별자              |

<br>

## NavController 생성

```kotlin
val navController = rememberNavController()
```

NavController는 화면 이동을 담당한다.

보통 최상위 Composable에서 생성한다.

<br>

## NavHost 구성

```kotlin
NavHost(
    navController = navController,
    startDestination = "home"
) {

    composable("home") {
        HomeScreen()
    }

    composable("detail") {
        DetailScreen()
    }
}
```

* startDestination → 앱 시작 화면
* composable → 이동 가능한 화면 등록

<br>

## 화면 이동

```kotlin
navController.navigate("detail")
```

Home 화면에서 Detail 화면으로 이동한다.

<br>
  
예제

```kotlin
Button(
    onClick = {
        navController.navigate("detail")
    }
) {
    Text("상세 화면 이동")
}
```

<br>

## 뒤로가기

```kotlin
navController.popBackStack()
```

현재 화면을 제거하고 이전 화면으로 돌아간다.

<br>

예제

```kotlin
Button(
    onClick = {
        navController.popBackStack()
    }
) {
    Text("뒤로가기")
}
```

<br>

## 전체 예제

```kotlin
@Composable
fun NavigationExample() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {

        composable("home") {
            HomeScreen(navController)
        }

        composable("detail") {
            DetailScreen(navController)
        }
    }
}

@Composable
fun HomeScreen(
    navController: NavController
) {

    Button(
        onClick = {
            navController.navigate("detail")
        }
    ) {
        Text("상세 화면 이동")
    }
}

@Composable
fun DetailScreen(
    navController: NavController
) {

    Button(
        onClick = {
            navController.popBackStack()
        }
    ) {
        Text("뒤로가기")
    }
}
```

<br>

## 화면 이동 흐름

```text
HomeScreen
     ↓
navigate("detail")
     ↓
DetailScreen
     ↓
popBackStack()
     ↓
HomeScreen
```

<br>

## route란?

route는 화면을 식별하기 위한 문자열이다.

```kotlin
composable("home") { }

composable("detail") { }
```

Navigation은 route를 기준으로 이동한다.

```kotlin
navController.navigate("detail")
```

<br>

## route 상수 관리

문자열을 직접 작성하기보다 상수로 관리하는 것이 좋다.

```kotlin
object Screen {

    const val HOME = "home"

    const val DETAIL = "detail"
}
```

사용

```kotlin
navController.navigate(Screen.DETAIL)
```

<br>

## 실무에서 사용하는 구조

```text
navigation/
├─ Screen.kt
├─ NavGraph.kt
├─ HomeScreen.kt
└─ DetailScreen.kt
```

<br>

Screen

```kotlin
object Screen {

    const val HOME = "home"

    const val DETAIL = "detail"
}
```

<br>

NavGraph

```kotlin
@Composable
fun AppNavGraph(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.HOME
    ) {

        composable(Screen.HOME) {
            HomeScreen(navController)
        }

        composable(Screen.DETAIL) {
            DetailScreen(navController)
        }
    }
}
```

<br>

## 사용 시 주의점

### route 문자열 하드코딩 지양

```kotlin
navController.navigate("detail") 보다
navController.navigate(Screen.DETAIL)
```

<br>

### NavController는 remember 사용

```kotlin
val navController = rememberNavController()
```

Composable이 재구성되어도 상태를 유지할 수 있다.

<br>

## 정리

* Navigation은 Compose 화면 이동 라이브러리
* NavController가 화면 이동을 담당
* NavHost가 화면을 관리
* composable로 목적지 등록
* navigate()로 화면 이동
* popBackStack()으로 이전 화면 복귀
* route로 화면을 식별
* 실무에서는 route를 상수로 관리하는 것이 좋음

<br> 

## 참고

* Android Developers - Navigation Compose
* https://developer.android.com/develop/ui/compose/navigation
