# popUpTo

<br>

## 목차

1. popUpTo란?
2. 왜 사용하는가?
3. Back Stack 이해하기
4. popUpTo 사용법
5. inclusive 옵션
6. launchSingleTop과 함께 사용하기
7. 실전 예제 - 로그인 화면 제거
8. 실전 예제 - 회원가입 완료 후 이동
9. 자주 하는 실수
10. 언제 사용하면 좋을까?
11. 정리

<br>

## 1. popUpTo란?

popUpTo는 Navigation의 Back Stack(뒤로가기 스택)을 원하는 위치까지 제거하는 옵션이다.

단순히 화면을 이동하는 것이 아니라, 이전 화면들을 스택에서 없애고 이동할 수 있다.

예를 들어 로그인에서 홈으로 이동했는데 뒤로가기를 눌렀을 때 다시 로그인 화면이 나오면 이상하다.

이럴 때 popUpTo를 사용한다.

<br>

## 2. 왜 사용하는가?

navigate()만 사용하면 이전 화면은 그대로 Back Stack에 남는다.

```
Login
 ↓
Home
 ↓
(뒤로가기)
 ↓
Login
```

사용자는 다시 로그인 화면으로 돌아간다.

하지만 로그인이 끝난 뒤에는 로그인 화면으로 돌아갈 이유가 없다.

따라서

- 로그인 완료
- 회원가입 완료
- 온보딩 완료

같이 다시 돌아오면 안 되는 화면에서는 Back Stack을 제거해야 한다.

<br>

## 3. Back Stack 이해하기


```
Splash
 ↓
Login
 ↓
Home
```

Back Stack은 Splash , Login, Home 이다.

뒤로가기를 누르면

```
Home 제거
```

↓

```
Splash
Login
```

↓

Login 화면이 나타난다.

popUpTo는 "어디까지 스택을 지울지" 를 지정하는 기능이다.

<br>

## 4. popUpTo 사용법

```kotlin
navController.navigate("home") {
    popUpTo("login")
}
```

이 경우

```
Login
 ↓
Home
```

으로 이동하면서

login까지 Back Stack을 제거한다.

하지만 inclusive를 지정하지 않았기 때문에 login은 남는다.

<br>

## 5. inclusive 옵션

inclusive=false (기본값)

```kotlin
navController.navigate("home") {
    popUpTo("login")
}
```

결과

```
Login
Home
```


<br>

inclusive=true

```kotlin
navController.navigate("home") {
    popUpTo("login") {
        inclusive = true
    }
}
```

결과

```
Home
```

login도 삭제된다.

로그인 화면을 완전히 제거하고 싶다면 보통 inclusive=true를 사용한다.

<br>

## 6. launchSingleTop과 함께 사용하기

많이 사용하는 형태는 아래와 같다.

```kotlin
navController.navigate("home") {
    popUpTo("login") {
        inclusive = true
    }
    launchSingleTop = true
}
```

launchSingleTop은 이미 Home이 최상단이라면 새로운 Home을 또 만들지 않는다.

중복 화면 생성을 막을 수 있다.

<br>

## 7. 실전 예제 - 로그인 화면 제거

로그인 성공

```kotlin
Button(
    onClick = {
        navController.navigate("home") {
            popUpTo("login") {
                inclusive = true
            }
        }
    }
) {
    Text("로그인")
}
```

동작

```
Login
 ↓
Home
```

뒤로가기

앱 종료

로그인 화면으로 돌아가지 않는다.

<br>

# 8. 실전 예제 - 회원가입 완료 후 이동

회원가입

```
Login
 ↓
SignUp
```

회원가입 완료

```
Home
```

코드

```kotlin
navController.navigate("home") {
    popUpTo("login") {
        inclusive = true
    }
}
```

결과

```
Home
```

뒤로가기

앱 종료

회원가입 화면도 로그인 화면도 나타나지 않는다.

<br>

## 9. 자주 하는 실수

### 1) navigate만 사용하는 경우

```kotlin
navController.navigate("home")
```

Back Stack

```
Login
Home
```

뒤로가기를 누르면 Login으로 돌아간다.

<br>

### 2) inclusive를 빼먹는 경우

```kotlin
popUpTo("login")
```

login은 제거되지 않는다.

로그인 화면까지 없애고 싶다면 다음과 같이 설정해야 한다.

```kotlin
inclusive = true
```

<br>

### 3) launchSingleTop을 사용하지 않는 경우

```kotlin
Home
 ↓
Home
 ↓
Home
```

같은 화면이 계속 생성될 수 있다.

<br>

## 10. 언제 사용하면 좋을까?

다음과 같은 상황에서 거의 항상 사용한다.

- 로그인 완료
- 회원가입 완료
- 온보딩 종료
- Splash 종료
- 결제 완료 화면
- 완료(Complete) 화면
- 다시 돌아오면 안 되는 화면

반대로

- 상세 화면
- 설정 화면
- 프로필 화면

처럼 뒤로가기가 필요한 경우에는 사용하지 않는 것이 좋다.

<br>

## 11. 정리

- popUpTo는 Back Stack을 정리하는 기능이다.
- navigate()만 사용하면 이전 화면이 그대로 남는다.
- inclusive=true를 사용하면 지정한 화면까지 함께 제거된다.
- 로그인, 회원가입, 온보딩처럼 다시 돌아올 필요가 없는 화면에서 자주 사용한다.
- launchSingleTop과 함께 사용하면 같은 화면이 여러 번 생성되는 것을 막을 수 있다.
- Navigation에서는 화면 이동만큼 Back Stack 관리도 중요하다.
