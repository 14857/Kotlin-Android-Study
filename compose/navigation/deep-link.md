# Deep Link

목차

1. Deep Link란?
2. 왜 사용하는가?
3. Deep Link 종류
4. Navigation에서 Deep Link 설정
5. URL로 화면 이동하기
6. 앱이 꺼져 있을 때 동작
7. 앱이 실행 중일 때 동작
8. NavArgument와 함께 사용하기
9. 실무 사용 예시
10. 주의사항
11. 정리

<br>

## 1. Deep Link란?

Deep Link는 앱의 특정 화면으로 바로 이동할 수 있는 링크이다.

예를 들어 일반적으로는

```text
앱 실행
→ 홈 화면
→ 게시글 목록
→ 게시글 상세
```

순서로 이동해야 한다.

하지만 Deep Link를 사용하면

```text
링크 클릭
→ 게시글 상세 화면 바로 이동
```

이 가능하다.

예시 URL

```text
https://example.com/post/123
```

사용자가 해당 링크를 클릭하면 앱이 열리면서 게시글 123 화면으로 바로 이동할 수 있다.

<br>

## 2. 왜 사용하는가?

앱 사용성을 높이기 위해 사용한다.

대표적인 사용 사례

* 푸시 알림 클릭
* 이메일 링크 클릭
* 웹사이트에서 앱 열기
* 친구 초대 링크
* 공유 링크

예시

```text
"새 녹음이 완료되었습니다."
```

알림 클릭

```text
앱 실행
→ 녹음 상세 화면
```

홈 화면을 거치지 않고 바로 이동할 수 있다.

<br>

## 3. Deep Link 종류

### Explicit Deep Link

Intent를 직접 생성해서 이동

```kotlin
val intent = Intent(context, DetailActivity::class.java)
intent.putExtra("id", 1)

startActivity(intent)
```

Activity 기반에서 많이 사용

<br>

### Implicit Deep Link

URL을 이용해 이동

```text
https://example.com/post/1
```

웹 브라우저에서도 열 수 있다.

Compose Navigation에서 주로 사용하는 방식이다.

<br>

### App Link

Android가 공식적으로 지원하는 Deep Link

```text
https://myapp.com
```

도메인 소유 인증을 거친다.

사용자 경험이 가장 좋다.

<br>

## 4. Navigation에서 Deep Link 설정

게시글 상세 화면

```kotlin
NavHost(
    navController = navController,
    startDestination = "home"
) {

    composable(
        route = "detail/{id}",
        deepLinks = listOf(
            navDeepLink {
                uriPattern = "https://myapp.com/detail/{id}"
            }
        )
    ) { backStackEntry ->

        val id =
            backStackEntry.arguments?.getString("id")

        DetailScreen(id = id ?: "")
    }
}
```

설명

```kotlin
navDeepLink {
    uriPattern = "https://myapp.com/detail/{id}"
}
```

사용자가 아래 링크를 클릭하면

```text
https://myapp.com/detail/100
```

자동으로

```text
detail/100
```

화면으로 이동한다.

<br>

## 5. URL로 화면 이동하기

예시 URL

```text
https://myapp.com/detail/100
```

Deep Link 설정

```kotlin
navDeepLink {
    uriPattern = "https://myapp.com/detail/{id}"
}
```

결과

```text
id = 100
```

자동 추출

```kotlin
val id =
    backStackEntry.arguments?.getString("id")
```

일반 NavArgument와 동일하게 사용할 수 있다.

<br>

## 6. 앱이 꺼져 있을 때 동작

상황

```text
앱 종료 상태
```

사용자가 링크 클릭

```text
https://myapp.com/detail/100
```

동작 순서

```text
앱 실행
→ Navigation 생성
→ detail/100 이동
```

사용자는 앱이 처음부터 해당 화면으로 열린 것처럼 느낀다.

<br>

## 7. 앱이 실행 중일 때 동작

상황

```text
앱 사용 중
```

링크 클릭

```text
https://myapp.com/detail/100
```

동작

```text
현재 화면
→ detail/100 이동
```

새 Activity를 만들지 않고 Navigation BackStack에 추가된다.

<br>

## 8. NavArgument와 함께 사용하기

복수 파라미터 전달

```kotlin
composable(
    route = "detail/{id}/{type}",
    deepLinks = listOf(
        navDeepLink {
            uriPattern =
                "https://myapp.com/detail/{id}/{type}"
        }
    )
) { backStackEntry ->

    val id =
        backStackEntry.arguments?.getString("id")

    val type =
        backStackEntry.arguments?.getString("type")
}
```

링크

```text
https://myapp.com/detail/10/music
```

결과

```text
id = 10
type = music
```

<br>

## 9. 실무 사용 예시

### 1) 푸시 알림

알림

```text
새 녹음이 생성되었습니다.
```

클릭

```text
record/15
```

바로 이동

<br>

### 2) 공유 링크

친구에게 링크 공유

```text
https://myapp.com/record/15
```

클릭 시

```text
녹음 상세 화면
```

이동

<br>

### 3) 이메일 인증

메일

```text
계정 인증하기
```

버튼 클릭

```text
https://myapp.com/verify/123
```

앱 실행 후 인증 화면 이동

<br>

### 4) 차곡 프로젝트 예시

회의록 공유

```text
https://chagok.com/transcript/25
```

클릭

```text
TranscriptDetailScreen
```

바로 이동

실제로 STT 결과 공유 기능을 만들 때 활용할 수 있다.

<br>

## 10. 주의사항

### 1) AndroidManifest 설정 필요

Deep Link가 동작하려면 Activity에서 Intent Filter를 등록해야 한다.

```xml
<intent-filter>
    <action
        android:name="android.intent.action.VIEW"/>

    <category
        android:name="android.intent.category.DEFAULT"/>

    <category
        android:name="android.intent.category.BROWSABLE"/>

    <data
        android:scheme="https"
        android:host="myapp.com"/>
</intent-filter>
```

설정하지 않으면 링크를 눌러도 앱이 열리지 않는다.

<br>

### 2) URI와 Route를 일치시키기

잘못된 예

```kotlin
route = "detail/{id}"

uriPattern = "https://myapp.com/post/{id}"
```

가능은 하지만 유지보수가 어렵다.

같은 의미로 맞추는 것이 좋다.

<br>

### 3) Nullable 처리

```kotlin
val id =
    backStackEntry.arguments?.getString("id")
```

항상 null 가능성을 고려해야 한다.

<br>

### 4) 보안 주의

Deep Link 데이터는 외부에서 전달된다.

따라서

```kotlin
id.toInt()
```

같은 코드보다

```kotlin
id?.toIntOrNull()
```

처럼 안전하게 처리하는 것이 좋다.

<br>

## 11. 정리

* Deep Link는 앱의 특정 화면으로 바로 이동하는 기능이다.
* 푸시 알림, 공유 링크, 이메일 인증 등에 많이 사용된다.
* Compose Navigation에서는 `navDeepLink()` 로 설정한다.
* URL의 값을 NavArgument처럼 사용할 수 있다.
* 앱이 꺼져 있어도 원하는 화면으로 바로 이동 가능하다.
* AndroidManifest의 Intent Filter 설정이 필요하다.
* 외부 데이터이므로 항상 안전하게 검증해야 한다.

Deep Link를 이해하면 Navigation이 단순 화면 이동을 넘어서 웹, 알림, 공유 기능과 연결되는 구조를 이해할 수 있다.
