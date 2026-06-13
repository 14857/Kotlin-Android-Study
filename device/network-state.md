# Network State

## 목차

1. Network State란?
2. 왜 필요한가?
3. ConnectivityManager
4. NetworkCallback
5. 네트워크 상태 감지하기
6. Compose에서 사용하기
7. ViewModel과 함께 사용하기
8. 실무에서 주의할 점
9. Android XML 방식과 비교
10. 정리

# 1. Network State란?

Network State는 현재 기기의 인터넷 연결 상태를 의미한다.

예를 들어 다음과 같은 상태를 알 수 있다.

- Wi-Fi 연결
- 모바일 데이터 연결
- 인터넷 연결 끊김
- 네트워크 변경

실제 앱에서는 네트워크 상태에 따라 화면을 다르게 보여주는 경우가 많다.

예시

- 인터넷 없음 화면 표시
- 자동 재시도
- API 호출 제한
- 오프라인 모드 제공

<br>

# 2. 왜 필요한가?

인터넷이 없는 상태에서 API를 호출하면 오류가 발생한다.

```kotlin
repository.getUser()
```

만약 인터넷이 끊겨있다면

```text
Network Error
Timeout
UnknownHostException
```

같은 예외가 발생할 수 있다.

그래서 네트워크 상태를 미리 감지하고 사용자에게 안내하는 것이 좋다.

```text
인터넷 연결을 확인해주세요.
```

<br>

# 3. ConnectivityManager

Android에서 네트워크 상태를 관리하는 시스템 서비스이다.

```kotlin
val connectivityManager =
    context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager
```

왜 사용하는가?

Android OS가 직접 관리하는 네트워크 정보를 가져오기 위해서이다.

여기서

```kotlin
Context.CONNECTIVITY_SERVICE
```

는

```text
네트워크 관련 시스템 서비스 주세요
```

라는 의미이다.

<br>

# 4. NetworkCallback

과거에는 BroadcastReceiver를 사용했다.

하지만 Android 7.0 이후부터는 NetworkCallback 사용이 권장된다.

```kotlin
val callback = object : ConnectivityManager.NetworkCallback() {

}
```

Network 상태가 변경될 때 호출된다.

대표적으로

```kotlin
onAvailable()
```

인터넷 연결됨

```kotlin
onLost()
```

인터넷 연결 끊김

예시

```kotlin
val callback = object : ConnectivityManager.NetworkCallback() {

    override fun onAvailable(network: Network) {
        Log.d("Network", "Connected")
    }

    override fun onLost(network: Network) {
        Log.d("Network", "Disconnected")
    }
}
```

<br>

# 5. 네트워크 상태 감지하기

현재 인터넷 연결 여부 확인

```kotlin
fun isConnected(
    context: Context
): Boolean {

    val connectivityManager =
        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

    val network =
        connectivityManager.activeNetwork ?: return false

    val capabilities =
        connectivityManager.getNetworkCapabilities(network)
            ?: return false

    return capabilities.hasCapability(
        NetworkCapabilities.NET_CAPABILITY_INTERNET
    )
}
```

코드 해석

```kotlin
val network =
    connectivityManager.activeNetwork
```

현재 사용 중인 네트워크 가져오기

```kotlin
val capabilities =
    connectivityManager.getNetworkCapabilities(network)
```

네트워크 정보 가져오기

```kotlin
capabilities.hasCapability(
    NetworkCapabilities.NET_CAPABILITY_INTERNET
)
```

인터넷 사용 가능한지 확인

실행 결과

```text
true
```

인터넷 연결됨

```text
false
```

인터넷 연결 안됨

<br>

# 6. Compose에서 사용하기

예를 들어 인터넷 연결이 없을 때 다른 화면을 보여줄 수 있다.

```kotlin
if (isConnected) {
    ContentScreen()
} else {
    NoInternetScreen()
}
```

예시

```kotlin
@Composable
fun MainScreen(
    isConnected: Boolean
) {

    if (isConnected) {
        Text("인터넷 연결됨")
    } else {
        Text("인터넷 연결 안됨")
    }
}
```

<br>

# 7. ViewModel과 함께 사용하기

실무에서는 ViewModel이 네트워크 상태를 관리하는 경우가 많다.

```kotlin
class NetworkViewModel : ViewModel() {

    private val _isConnected =
        MutableStateFlow(false)

    val isConnected =
        _isConnected.asStateFlow()
}
```

Compose에서 구독

```kotlin
@Composable
fun MainScreen(
    viewModel: NetworkViewModel
) {

    val isConnected by
        viewModel.isConnected.collectAsState()

    if (isConnected) {
        ContentScreen()
    } else {
        NoInternetScreen()
    }
}
```

왜 StateFlow를 사용할까?

```text
네트워크 상태가 변경되면
UI가 자동으로 다시 그려져야 하기 때문
```

<br>

# 8. 실무에서 주의할 점

## 인터넷 연결 ≠ 서버 연결

많이 헷갈리는 부분이다.

```kotlin
NetworkCapabilities.NET_CAPABILITY_INTERNET
```

이 true라고 해서 서버가 정상이라는 뜻은 아니다.

예시

```text
Wi-Fi 연결됨
API 서버 다운
```

이 경우

```text
인터넷 있음
서버 응답 없음
```

상태가 된다.

<br>

## 네트워크 상태만 믿지 말 것

실무에서는

```text
네트워크 체크
+
API 호출 예외 처리
```

둘 다 해야 한다.

```kotlin
try {
    repository.getData()
} catch (e: Exception) {

}
```

<br>

## 화면마다 감지하지 말 것

안 좋은 예

```text
ScreenA
ScreenB
ScreenC
```

각 화면마다 네트워크 상태 감지

좋은 예

```text
Application

또는

Singleton Manager

또는

ViewModel
```

한 곳에서 관리

<br>

# 9. Android XML 방식과 비교

예전 Android View 방식

```kotlin
networkCallback.onAvailable {
    textView.text = "연결됨"
}

networkCallback.onLost {
    textView.text = "끊김"
}
```

UI를 직접 수정해야 한다.

Compose 방식

```kotlin
if (isConnected) {
    ConnectedScreen()
} else {
    DisconnectedScreen()
}
```

상태만 변경하면 UI가 자동으로 갱신된다.

비교

| Android XML | Compose |
|------------|----------|
| View 직접 수정 | State 변경 |
| textView.text | StateFlow |
| 수동 UI 갱신 | 자동 Recomposition |
| Callback 중심 | State 중심 |

<br>

# 많이 헷갈리는 부분

## activeNetwork가 null인 이유

```kotlin
val network =
    connectivityManager.activeNetwork
```

이 값이 null이면

```text
현재 연결된 네트워크 없음
```

을 의미한다.

즉

```text
Wi-Fi 꺼짐
모바일 데이터 꺼짐
비행기 모드
```

등의 상황이다.

<br>

## onAvailable만 믿으면 안 된다

```kotlin
override fun onAvailable(
    network: Network
)
```

가 호출됐다고 해서 API가 성공하는 것은 아니다.

```text
인터넷 연결됨
```

만 의미한다.

실제 서버 상태는 API 호출 결과로 확인해야 한다.

<br>

# 실무에서 많이 사용하는 NetworkMonitor 예시

```kotlin
class NetworkMonitor(
    context: Context
) {

    private val connectivityManager =
        context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager

    private val _isConnected =
        MutableStateFlow(false)

    val isConnected =
        _isConnected.asStateFlow()

    private val callback =
        object : ConnectivityManager.NetworkCallback() {

            override fun onAvailable(
                network: Network
            ) {
                _isConnected.value = true
            }

            override fun onLost(
                network: Network
            ) {
                _isConnected.value = false
            }
        }

    fun start() {
        connectivityManager.registerDefaultNetworkCallback(
            callback
        )
    }

    fun stop() {
        connectivityManager.unregisterNetworkCallback(
            callback
        )
    }
}
```

왜 이렇게 만들까?

```text
네트워크 상태를 한 곳에서 관리하고
모든 화면이 동일한 상태를 구독할 수 있기 때문
```

실제 회사 프로젝트에서도 이런 형태로 구현하는 경우가 많다.

<br>

# 정리

- Network State는 인터넷 연결 상태를 의미한다.
- Android에서는 ConnectivityManager를 사용한다.
- 네트워크 변경 감지는 NetworkCallback을 사용한다.
- Compose에서는 StateFlow와 함께 사용하는 경우가 많다.
- 인터넷 연결과 서버 정상 동작은 다른 개념이다.
- 실무에서는 네트워크 상태 확인과 API 예외 처리를 함께 해야 한다.
- 네트워크 상태는 한 곳에서 관리하는 것이 좋다.
- Compose는 상태(State)만 변경하면 UI가 자동으로 갱신된다.
