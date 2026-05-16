# FCM / APNs

## FCM / APNs란?

모바일 앱에서 Push Notification을 보내기 위해서는
운영체제가 제공하는 Push 서버를 반드시 사용해야 한다.

Android와 iOS는 각각 다른 Push 시스템을 사용한다.

| 플랫폼 | Push 시스템 |
|---|---|
| Android | FCM |
| iOS | APNs |

<br>

### Push Notification 전달 구조

Push Notification은 일반적으로 다음과 같은 흐름으로 동작한다.

```text
앱 서버
   ↓
Push 서버 (FCM / APNs)
   ↓
사용자 스마트폰
```

앱 서버가 직접 스마트폰에 접근하는 것이 아니라, Google 또는 Apple 서버를 거쳐 전달된다.

<br>
<br>

## FCM (Firebase Cloud Messaging)

### FCM이란?

Firebase Cloud Messaging으로 Google이 제공하는 Android Push Notification 서비스이다.

Android 앱에서 Push 알림을 보낼 때 가장 많이 사용한다.

<br>

### FCM의 역할

FCM은 단순히 메시지만 전달하는 것이 아니라 다음과 같은 기능을 함께 처리한다.

- 디바이스 식별
- 네트워크 상태 관리
- 절전 상태 대응
- 알림 재전송
- 대량 알림 처리

> 정리하자면, Push 알림 전달을 Google이 대신 처리해주는 시스템

<br>

## Device Token

Device Token은 Push Notification을 특정 디바이스(스마트폰)에게 보내기 위한 고유 식별값이다.

앱이 FCM(Android) 또는 APNs(iOS)에 등록되면,
Push 서버가 해당 디바이스에 대한 Token을 발급한다.

앱 서버는 이 Token을 저장한 뒤,
알림이 필요할 때 해당 Token으로 Push Notification을 전송한다.


### Device Token 발급 과정

앱 설치 후:

1. 앱이 FCM 서버에 연결
2. FCM이 Device Token 발급
3. 앱 서버가 Token 저장
4. 서버가 특정 사용자에게 알림 전송 가능


<br>

#### FCM 예시 코드 (Android)

```kotlin
class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)

        println("FCM Token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        println(message.notification?.title)
    }
}
```

<br>
<br>

## APNs (Apple Push Notification service)

### APNs란?

APNs(Apple Push Notification service)는 Apple이 제공하는 iOS Push Notification 서비스이다.

iPhone 앱은 반드시 APNs를 통해 Push 알림을 전달해야 한다.

<br>

### APNs 특징

| 항목 | 설명 |
|---|---|
| 운영사 | Apple |
| 대상 플랫폼 | iOS |
| 앱 종료 상태 수신 | 가능 |
| 시스템 알림 지원 | O |
| 절전 최적화 | 매우 강력 |

<br>

## FCM vs APNs 비교

| 항목 | FCM | APNs |
|---|---|---|
| 운영사 | Google | Apple |
| 플랫폼 | Android | iOS |
| 정식 이름 | Firebase Cloud Messaging | Apple Push Notification service |
| 역할 | Push 알림 전달 | Push 알림 전달 |
| 앱 종료 상태 수신 | 가능 | 가능 |

<br>

## FCM과 APNs의 공통점

### 앱이 꺼져 있어도 동작

운영체제가 직접 관리하기 때문에 앱 종료 상태에서도 알림 수신이 가능하다.

### 운영체제 기반 시스템
Push Notification은 Android OS,  iOS 레벨에서 동작하는 기능이다.

### 실시간 알림 전달

다음과 같은 기능에 매우 적합하다.

- 채팅 알림
- 지연 알림
- 이벤트 알림
- 공지사항

<br>

## FCM / APNs의 한계

Push Notification은 강력하지만
완전한 실시간 데이터 통신 기술은 아니다.

따라서:
- 실시간 UI 업데이트
- 실시간 게임 상태
- 실시간 스트리밍

같은 기능에는 적합하지 않다.

이런 경우에는 일반적으로 **WebSocket** 기술을 함께 사용한다.


---
<br>

참고

Firebase Cloud Messaging 공식 문서  
Apple Developer Documentation  
Android Developers  
Kotlin 공식 문법
