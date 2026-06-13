# BroadcastReceiver

## 목차

1. BroadcastReceiver란?
2. 왜 사용하는가?
3. 동작 원리
4. BroadcastReceiver 만들기
5. Manifest 등록
6. 동적 등록
7. unregisterReceiver가 필요한 이유
8. Compose에서 사용하기
9. DisposableEffect와 함께 사용하기
10. 실무 예제
11. 많이 헷갈리는 부분
12. 정리

# 1. BroadcastReceiver란?

BroadcastReceiver는 Android 시스템 또는 다른 앱이 보내는 이벤트를 수신하는 컴포넌트이다.

예를 들어 Android는 다양한 이벤트를 발생시킨다.

```text
배터리 상태 변경
인터넷 연결 변경
비행기 모드 변경
화면 ON/OFF
부팅 완료
```

BroadcastReceiver는 이러한 이벤트를 감지할 수 있다.

<br>

# 2. 왜 사용하는가?

특정 이벤트가 발생했을 때 자동으로 작업을 수행하기 위해 사용한다.

예를 들어

```text
인터넷 연결 끊김
↓
오프라인 화면 표시
```

```text
배터리 부족
↓
백그라운드 작업 중지
```

```text
기기 부팅 완료
↓
자동 초기화 작업 수행
```

사용자가 직접 버튼을 누르지 않아도 동작할 수 있다.

<br>

# 3. 동작 원리

Android 시스템

```text
배터리 변경 발생
```

↓

Broadcast 전송

↓

BroadcastReceiver 수신

↓

onReceive() 실행

구조

```text
Android System
        ↓
    Broadcast
        ↓
BroadcastReceiver
        ↓
    onReceive()
```

<br>

# 4. BroadcastReceiver 만들기

기본 구조

```kotlin
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {

    }
}
```

왜 onReceive를 사용할까?

```text
Broadcast가 도착했을 때
실행되는 함수이기 때문
```

<br>

예시

```kotlin
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent
    ) {
        Log.d(
            "Receiver",
            "Broadcast Received"
        )
    }
}
```

Broadcast를 수신하면 로그가 출력된다.

<br>

# 5. Manifest 등록

앱 실행 여부와 상관없이 수신이 필요한 경우 사용한다.

AndroidManifest.xml

```xml
<receiver
    android:name=".MyReceiver" />
```

일부 Broadcast는 Manifest 등록만으로 수신 가능하다.

예시

```text
BOOT_COMPLETED
```

기기 부팅 완료

<br>

Intent Filter 추가

```xml
<receiver
    android:name=".MyReceiver">

    <intent-filter>
        <action
            android:name="
            android.intent.action.BOOT_COMPLETED" />
    </intent-filter>

</receiver>
```

<br>

# 6. 동적 등록

실무에서는 동적 등록을 더 많이 사용한다.

```kotlin
val receiver = MyReceiver()

val filter = IntentFilter()

filter.addAction(
    Intent.ACTION_AIRPLANE_MODE_CHANGED
)

context.registerReceiver(
    receiver,
    filter
)
```

왜 동적 등록을 사용할까?

```text
필요한 화면에서만
Receiver를 사용할 수 있기 때문
```

<br>

해제

```kotlin
context.unregisterReceiver(
    receiver
)
```

<br>

# 7. unregisterReceiver가 필요한 이유

많이 헷갈리는 부분이다.

등록만 하고 해제하지 않으면

```text
메모리 누수
Crash
```

가 발생할 수 있다.

안 좋은 예

```kotlin
context.registerReceiver(
    receiver,
    filter
)
```

등록만 함

<br>

좋은 예

```kotlin
context.registerReceiver(
    receiver,
    filter
)

context.unregisterReceiver(
    receiver
)
```

등록과 해제를 함께 관리

<br>

# 8. Compose에서 사용하기

Compose에서는 보통 상태(State)를 변경한다.

```kotlin
var isAirplaneMode by
    mutableStateOf(false)
```

Broadcast 수신

```kotlin
override fun onReceive(
    context: Context,
    intent: Intent
) {
    isAirplaneMode = true
}
```

상태 변경

↓

Recomposition 발생

↓

UI 자동 갱신

<br>

# 9. DisposableEffect와 함께 사용하기

Compose에서 Receiver를 사용할 때 가장 많이 사용하는 패턴이다.

```kotlin
@Composable
fun AirplaneModeScreen() {

    val context = LocalContext.current

    DisposableEffect(Unit) {

        val receiver =
            object : BroadcastReceiver() {

                override fun onReceive(
                    context: Context,
                    intent: Intent
                ) {

                }
            }

        val filter =
            IntentFilter(
                Intent.ACTION_AIRPLANE_MODE_CHANGED
            )

        context.registerReceiver(
            receiver,
            filter
        )

        onDispose {

            context.unregisterReceiver(
                receiver
            )
        }
    }
}
```

왜 DisposableEffect를 사용할까?

```text
화면 진입
↓
Receiver 등록

화면 종료
↓
Receiver 해제
```

Lifecycle을 Compose 방식으로 관리하기 위해 사용한다.

<br>

이 부분은 많이 중요하다.

```kotlin
onDispose {
    context.unregisterReceiver(
        receiver
    )
}
```

이 코드가 없으면

```text
Receiver는 살아있는데
화면은 사라짐
```

상태가 될 수 있다.

<br>

# 10. 실무 예제

## 비행기 모드 감지

```kotlin
Intent.ACTION_AIRPLANE_MODE_CHANGED
```

<br>

## 화면 ON

```kotlin
Intent.ACTION_SCREEN_ON
```

<br>

## 화면 OFF

```kotlin
Intent.ACTION_SCREEN_OFF
```

<br>

## 배터리 상태 변경

```kotlin
Intent.ACTION_BATTERY_CHANGED
```

<br>

## 전원 연결

```kotlin
Intent.ACTION_POWER_CONNECTED
```

<br>

## 전원 해제

```kotlin
Intent.ACTION_POWER_DISCONNECTED
```

<br>

## 부팅 완료

```kotlin
Intent.ACTION_BOOT_COMPLETED
```

<br>

# 11. 많이 헷갈리는 부분

## BroadcastReceiver는 백그라운드 작업용이 아니다

잘못된 생각

```text
Receiver에서
오래 걸리는 작업 수행
```

<br>

예시

```kotlin
override fun onReceive(
    context: Context,
    intent: Intent
) {

    Thread.sleep(10000)
}
```

좋지 않다.

<br>

onReceive는 빠르게 끝나야 한다.

필요하면

```text
WorkManager
Service
Coroutine
```

등으로 작업을 넘겨야 한다.

<br>

## BroadcastReceiver와 Service 차이

BroadcastReceiver

```text
이벤트 감지
```

예시

```text
배터리 변경
인터넷 변경
비행기 모드 변경
```

<br>

Service

```text
오래 실행되는 작업
```

예시

```text
음악 재생
파일 다운로드
위치 추적
```

<br>

비교

| BroadcastReceiver | Service |
|----------|----------|
| 이벤트 수신 | 작업 수행 |
| 짧게 실행 | 오래 실행 |
| onReceive() | onStartCommand() |
| 상태 감지 | 백그라운드 처리 |

<br>

## NetworkCallback과 차이

예전 Android에서는

```kotlin
ConnectivityManager.CONNECTIVITY_ACTION
```

BroadcastReceiver로 네트워크 상태를 감지했다.

<br>

현재는

```kotlin
ConnectivityManager.NetworkCallback
```

사용이 권장된다.

즉

```text
네트워크 상태
→ NetworkCallback

기타 시스템 이벤트
→ BroadcastReceiver
```

라고 생각하면 된다.

<br>

# 정리

- BroadcastReceiver는 시스템 이벤트를 수신하는 컴포넌트이다.
- 이벤트가 발생하면 onReceive()가 호출된다.
- Manifest 등록 방식과 동적 등록 방식이 있다.
- 실무에서는 동적 등록을 많이 사용한다.
- 등록한 Receiver는 반드시 해제해야 한다.
- Compose에서는 DisposableEffect와 함께 사용하는 경우가 많다.
- BroadcastReceiver는 이벤트 감지용이다.
- 오래 걸리는 작업은 Service 또는 WorkManager로 처리해야 한다.
- 네트워크 상태는 BroadcastReceiver보다 NetworkCallback 사용이 권장된다.
