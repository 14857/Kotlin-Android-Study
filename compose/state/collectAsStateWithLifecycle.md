# collectAsStateWithLifecycle

## 목차

1. collectAsStateWithLifecycle란?
2. 왜 사용하는가?
3. collectAsState와 차이점
4. Lifecycle과 함께 동작하는 원리
5. 사용 준비
6. ViewModel에서 StateFlow 만들기
7. collectAsState 사용하기
8. collectAsStateWithLifecycle 사용하기
9. 실무에서 사용하는 패턴
10. 주의사항
11. 정리

<br>

## 1. collectAsStateWithLifecycle란?

`collectAsStateWithLifecycle()`은 `StateFlow`나 `Flow`를 Compose에서 사용할 수 있는 `State`로 변환하면서 Lifecycle까지 함께 관리해주는 API이다.

Compose는 `State`의 값이 변경되면 자동으로 화면을 다시 그린다. 하지만 `Flow`는 계속 데이터를 방출하기 때문에 화면이 보이지 않는 동안에도 계속 수집하면 불필요한 작업이 발생할 수 있다.

`collectAsStateWithLifecycle()`은 화면이 `STARTED` 상태일 때만 데이터를 수집하고, 화면이 백그라운드로 이동하면 자동으로 수집을 중단한다.

실무에서는 `StateFlow`를 UI에서 사용할 때 가장 많이 사용하는 방식이다.

<br>

## 2. 왜 사용하는가?

예를 들어 ViewModel에서 1초마다 값을 변경한다고 가정해보자.

```kotlin
class CounterViewModel : ViewModel() {

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000)
                _count.value++
            }
        }
    }
}
```

화면이 보이지 않아도 계속 값을 수집한다면 다음과 같은 문제가 생길 수 있다.

- 필요 없는 CPU 사용
- 불필요한 메모리 사용
- 화면에 표시되지 않는 데이터 계속 처리

`collectAsStateWithLifecycle()`을 사용하면 화면이 다시 보일 때까지 자동으로 수집을 멈춘다.

<br>

## 3. collectAsState와 차이점

|항목|collectAsState|collectAsStateWithLifecycle|
|---|---|---|
|StateFlow 수집|O|O|
|Lifecycle 고려|X|O|
|백그라운드 수집|O|X|
|실무 사용 빈도|낮음|높음|
|권장 여부|특별한 경우 사용|권장|

Compose 자체에서는 `collectAsState()`를 제공하지만, Android 앱에서는 대부분 `collectAsStateWithLifecycle()`을 사용하는 것이 권장된다.

<br>

## 4. Lifecycle과 함께 동작하는 원리

Compose 화면은 Activity나 Fragment의 Lifecycle을 따른다.

`collectAsStateWithLifecycle()`은 Lifecycle이 `STARTED` 이상일 때만 Flow를 수집한다.

예를 들어 Home 화면에서 Detail 화면으로 이동하면 Home 화면은 `STOPPED` 상태가 된다.

이때 Flow 수집도 함께 중단된다.

뒤로 돌아와 Home 화면이 다시 `STARTED` 상태가 되면 자동으로 Flow 수집을 다시 시작한다.

개발자가 직접 Lifecycle을 관리할 필요가 없다.

<br>

## 5. 사용 준비

### dependency 추가

```kotlin
implementation("androidx.lifecycle:lifecycle-runtime-compose:2.9.0")
```

### import

```kotlin
import androidx.lifecycle.compose.collectAsStateWithLifecycle
```

<br>

## 6. ViewModel에서 StateFlow 만들기

```kotlin
class CounterViewModel : ViewModel() {

    private val _count = MutableStateFlow(0)
    val count: StateFlow<Int> = _count

    fun increase() {
        _count.value++
    }
}
```

UI에서는 `count`만 사용하고, 값 변경은 ViewModel 내부에서만 수행한다.

이처럼 `MutableStateFlow`는 외부에 노출하지 않는 것이 일반적인 패턴이다.

<br>

## 7. collectAsState 사용하기

```kotlin
@Composable
fun CounterScreen(
    viewModel: CounterViewModel = viewModel()
) {
    val count by viewModel.count.collectAsState()

    Column {
        Text(text = "$count")

        Button(
            onClick = viewModel::increase
        ) {
            Text("증가")
        }
    }
}
```

동작에는 문제가 없지만 Lifecycle을 고려하지 않는다.

따라서 화면이 보이지 않는 동안에도 계속 Flow를 수집할 수 있다.

<br>

## 8. collectAsStateWithLifecycle 사용하기

```kotlin
@Composable
fun CounterScreen(
    viewModel: CounterViewModel = viewModel()
) {
    val count by viewModel.count.collectAsStateWithLifecycle()

    Column {
        Text(text = "$count")

        Button(
            onClick = viewModel::increase
        ) {
            Text("증가")
        }
    }
}
```

바뀐 부분은 하나뿐이다.

```kotlin
collectAsState()
```

↓

```kotlin
collectAsStateWithLifecycle()
```

하지만 내부에서는 Lifecycle을 감지하여 필요한 시점에만 Flow를 수집한다.

기존 코드 대부분을 그대로 유지하면서 Lifecycle까지 함께 관리할 수 있다는 것이 장점이다.

<br>

## 9. 실무에서 사용하는 패턴

ViewModel에서는 UI 상태를 하나의 `StateFlow`로 관리하는 경우가 많다.

```kotlin
data class UserUiState(
    val name: String = "",
    val age: Int = 0
)
```

```kotlin
class UserViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(UserUiState())
    val uiState: StateFlow<UserUiState> = _uiState
}
```

Compose에서는 다음처럼 사용한다.

```kotlin
@Composable
fun UserScreen(
    viewModel: UserViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column {
        Text(text = uiState.name)
        Text(text = "${uiState.age}")
    }
}
```

이 패턴은 MVVM + Compose 프로젝트에서 가장 많이 볼 수 있는 구조이다.

<br>

## 10. 주의사항

### StateFlow에서 가장 많이 사용한다.

`collectAsStateWithLifecycle()`은 `StateFlow`와 함께 사용하는 경우가 가장 많다.

물론 일반 `Flow`에서도 사용할 수 있지만, UI 상태를 관리할 때는 대부분 `StateFlow`를 사용한다.

### remember와 함께 사용할 필요가 없다.

잘못된 예

```kotlin
val state by remember {
    viewModel.uiState.collectAsStateWithLifecycle()
}
```

올바른 예

```kotlin
val state by viewModel.uiState.collectAsStateWithLifecycle()
```

`collectAsStateWithLifecycle()`이 이미 Compose의 State를 생성하고 관리하기 때문에 `remember`를 추가로 사용할 필요가 없다.

### StateFlow와 SharedFlow를 구분하자.

UI 상태를 표현할 때는 `StateFlow`를 사용하는 것이 일반적이다.

반면

- Toast
- Snackbar
- 화면 이동
- 이벤트 처리

처럼 한 번만 처리해야 하는 값은 `SharedFlow`나 `Channel`을 사용하는 것이 적절하다.

<br>

## 11. 정리

`collectAsStateWithLifecycle()`은 `Flow`를 Compose의 `State`로 변환하면서 Lifecycle을 함께 관리하는 API이다.

주요 장점은 다음과 같다.

- 화면이 보일 때만 Flow를 수집한다.
- 백그라운드에서 불필요한 작업을 줄일 수 있다.
- 별도의 Lifecycle 처리가 필요 없다.
- `StateFlow`와 함께 사용하는 것이 가장 일반적이다.
- Compose + MVVM 프로젝트에서 가장 많이 사용하는 패턴이다.

Android에서 Compose를 개발한다면 `StateFlow`를 UI에서 사용할 때는 `collectAsStateWithLifecycle()`을 기본 선택지로 사용하는 것을 권장한다.
