# DisposableEffect

## DisposableEffect란?

DisposableEffect는 Composable이 화면에 진입할 때 특정 작업을 수행하고, 화면에서 제거될 때 정리(Cleanup) 작업을 수행하기 위한 Side Effect API이다.

주로 리스너 등록, Observer 등록, BroadcastReceiver 등록과 같이 해제 작업이 필요한 경우 사용한다.

<br>

## 왜 사용할까?

Composable은 화면에 나타났다가 사라질 수 있다.

이때 등록한 리스너나 Observer를 해제하지 않으면 메모리 누수(Memory Leak)가 발생할 수 있다.

DisposableEffect는 Composable의 생명주기에 맞춰 등록과 해제를 함께 관리할 수 있도록 도와준다.

<br>

## 기본 구조

```kotlin
DisposableEffect(Unit) {

    // 초기 작업

    onDispose {

        // 정리 작업
    }
}
```
* DisposableEffect 블록 → Composable이 화면에 진입할 때 실행
* onDispose → Composable이 화면에서 제거될 때 실행

<br>

## 동작 순서

```text
Composable 진입
    ↓
DisposableEffect 실행
    ↓
리스너 등록
    ↓
화면 유지
    ↓
Composable 제거
    ↓
onDispose 실행
    ↓
리스너 해제
```

<br>


## 예제 1. Lifecycle Observer 등록

```kotlin
@Composable
fun LifecycleExample(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    DisposableEffect(lifecycleOwner) {

        val observer = LifecycleEventObserver { _, event ->
            Log.d("Lifecycle", event.name)
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
```
* Observer 등록
* Lifecycle 이벤트 수신
* 화면이 제거되면 Observer 해제

<br>

## 예제 2. BroadcastReceiver 등록

```kotlin
@Composable
fun NetworkReceiverExample() {

    val context = LocalContext.current

    DisposableEffect(Unit) {

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context?,
                intent: Intent?
            ) {
                Log.d("Receiver", "수신 완료")
            }
        }

        context.registerReceiver(
            receiver,
            IntentFilter("sample.action")
        )

        onDispose {
            context.unregisterReceiver(receiver)
        }
    }
}
```
* Receiver 등록
* Broadcast 수신
* 화면 종료 시 Receiver 해제

<br>

## 예제 3. Sensor Listener 등록

```kotlin
DisposableEffect(Unit) {

    sensorManager.registerListener(
        listener,
        sensor,
        SensorManager.SENSOR_DELAY_NORMAL
    )

    onDispose {
        sensorManager.unregisterListener(listener)
    }
}
```
센서 사용이 끝나면 반드시 Listener를 해제해야 한다.

<br>

## key의 역할

DisposableEffect는 key 값이 변경되면 기존 Effect를 제거한 뒤 다시 생성한다.

```kotlin
DisposableEffect(userId) {

    Log.d("DisposableEffect", "등록")

    onDispose {
        Log.d("DisposableEffect", "해제")
    }
}
```

userId가 변경되면 다음과 같은 순서로 동작한다.

```text
기존 Effect 해제
    ↓
onDispose 실행
    ↓
새로운 Effect 생성
```

<br>

## LaunchedEffect와 차이

| 항목            | LaunchedEffect | DisposableEffect |
| ------------- | -------------- | ---------------- |
| 목적            | Coroutine 실행   | 등록/해제 관리         |
| suspend 함수 사용 | O              | X                |
| onDispose 제공  | X              | O                |
| 리스너 관리        | X              | O                |
| Observer 관리   | X              | O                |

LaunchedEffect

```kotlin
LaunchedEffect(Unit) {
    delay(1000)
}
```

DisposableEffect

```kotlin
DisposableEffect(Unit) {

    onDispose {

    }
}

```

<br>

## 언제 사용할까?

사용 예시

* BroadcastReceiver 등록/해제
* Sensor Listener 등록/해제
* Lifecycle Observer 등록/해제
* 콜백 등록/해제
* 외부 라이브러리 Listener 관리

<br>

## 사용 시 주의점

### onDispose는 반드시 작성해야 한다

```kotlin
DisposableEffect(Unit) {

    onDispose {

    }
}
```

DisposableEffect는 항상 onDispose를 포함해야 한다.

<br>

### suspend 함수 실행 용도가 아니다

```kotlin
DisposableEffect(Unit) {

    onDispose {

    }
}
```

Coroutine 실행이 목적이라면 LaunchedEffect를 사용한다.

DisposableEffect는 등록/해제 관리에 집중하는 API이다.

<br>

## 정리

* DisposableEffect는 등록과 해제가 필요한 작업을 처리하는 Side Effect API
* Composable이 생성될 때 실행
* Composable이 제거될 때 onDispose 실행
* Lifecycle Observer 관리 가능
* BroadcastReceiver 관리 가능
* Sensor Listener 관리 가능
* key 변경 시 기존 Effect를 제거한 뒤 다시 생성
* Coroutine 실행 목적이라면 LaunchedEffect 사용

<br>

## 참고

* Android Developers - Side effects in Compose
* https://developer.android.com/develop/ui/compose/side-effects
