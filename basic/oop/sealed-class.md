# Sealed Class

## 목차

1. Sealed Class란?
2. 왜 사용할까?
3. 기본 사용법
4. when과 함께 사용하기
5. Enum Class와 차이점
6. 실무에서 많이 사용하는 예시
7. 주의할 점
8. 정리

<br>

# 1. Sealed Class란?

Sealed Class는 상속 가능한 타입의 종류를 제한할 수 있는 클래스이다.

일반적인 open class는 어디서든 상속할 수 있지만, sealed class는 미리 정의된 클래스만 상속할 수 있다.

```kotlin
sealed class Result

class Success : Result()
class Error : Result()
class Loading : Result()
```

위 코드에서 Result를 상속하는 클래스는 미리 정의된 세 개뿐이다.

<br>

# 2. 왜 사용할까?

상태(State)를 표현할 때 매우 유용하다.

예를 들어 서버 통신 결과는 보통 다음 세 가지 상태를 가진다.

* 로딩 중
* 성공
* 실패

이런 상태를 String이나 Boolean으로 관리하면 실수하기 쉽다.

```kotlin
val state = "success"
```

오타가 발생해도 컴파일러가 잡아주지 못한다.

반면 Sealed Class를 사용하면 가능한 상태를 제한할 수 있다.

```kotlin
sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val message: String) : UiState()
}
```

컴파일러가 모든 상태를 체크해주기 때문에 더 안전하다.

<br>

# 3. 기본 사용법

```kotlin
sealed class NetworkState {
    data object Loading : NetworkState()

    data class Success(
        val data: String
    ) : NetworkState()

    data class Error(
        val message: String
    ) : NetworkState()
}
```

사용 예시

```kotlin
val state: NetworkState =
    NetworkState.Success("데이터 조회 완료")
```

Success는 데이터를 가질 수 있고

```kotlin
NetworkState.Success("데이터 조회 완료")
```

Error는 에러 메시지를 가질 수 있다.

```kotlin
NetworkState.Error("네트워크 오류")
```

<br>

# 4. when과 함께 사용하기

Sealed Class의 가장 큰 장점은 when과 함께 사용할 때 나타난다.

```kotlin
fun handleState(state: NetworkState) {
    when (state) {
        is NetworkState.Loading -> {
            println("로딩 중")
        }

        is NetworkState.Success -> {
            println(state.data)
        }

        is NetworkState.Error -> {
            println(state.message)
        }
    }
}
```

else가 필요 없다.

왜냐하면 컴파일러가 가능한 모든 타입을 알고 있기 때문이다.

만약 새로운 상태를 추가하면

```kotlin
data object Empty : NetworkState()
```

컴파일 에러가 발생한다.

```kotlin
when (state) {
    ...
}
```

에서 Empty 처리를 하지 않았기 때문이다.

즉, 상태 누락을 컴파일 단계에서 발견할 수 있다.

<br>

# 5. Enum Class와 차이점

Enum

```kotlin
enum class Result {
    SUCCESS,
    ERROR,
    LOADING
}
```

장점

* 간단하다
* 값이 고정적이다

단점

* 데이터를 가질 수 없다

<br>

Sealed Class

```kotlin
sealed class Result {
    data object Loading : Result()

    data class Success(
        val data: String
    ) : Result()

    data class Error(
        val message: String
    ) : Result()
}
```

장점

* 상태별로 다른 데이터를 가질 수 있다
* 타입 안정성이 높다
* 실무에서 UI State 표현에 많이 사용한다

<br>

헷갈리는 기준

단순한 상수 목록이면 Enum

```kotlin
enum class Language {
    KOREAN,
    ENGLISH,
    JAPANESE
}
```

상태마다 다른 데이터를 가져야 하면 Sealed Class

```kotlin
sealed class LoginState
```

<br>

# 6. 실무에서 많이 사용하는 예시

API 응답 상태

```kotlin
sealed class ApiResult<T> {

    data object Loading : ApiResult<Nothing>()

    data class Success<T>(
        val data: T
    ) : ApiResult<T>()

    data class Error(
        val message: String
    ) : ApiResult<Nothing>()
}
```

사용

```kotlin
val result: ApiResult<String> =
    ApiResult.Success("응답 성공")
```

<br>

Compose UI State

```kotlin
sealed class HomeUiState {

    data object Loading : HomeUiState()

    data class Success(
        val items: List<String>
    ) : HomeUiState()

    data class Error(
        val message: String
    ) : HomeUiState()
}
```

ViewModel

```kotlin
var uiState: HomeUiState =
    HomeUiState.Loading
```

Compose

```kotlin
when (uiState) {
    is HomeUiState.Loading -> {}

    is HomeUiState.Success -> {}

    is HomeUiState.Error -> {}
}
```

MVVM, MVI, Clean Architecture에서 매우 자주 볼 수 있는 패턴이다.

<br>

# 7. 주의할 점

Sealed Class는 "상태"를 표현할 때 사용하는 경우가 많다.

다음과 같은 경우에는 Enum이 더 적합하다.

```kotlin
enum class Theme {
    LIGHT,
    DARK
}
```

상태별로 추가 데이터가 필요하지 않다면 Enum을 사용하는 것이 더 단순하다.

<br>

또한 너무 많은 상태를 하나의 Sealed Class에 넣으면 관리가 어려워질 수 있다.

```kotlin
sealed class AppState
```

보다는

```kotlin
sealed class LoginState

sealed class HomeState
```

처럼 화면별로 나누는 것이 좋다.

<br>

# 8. 정리

* Sealed Class는 상속 가능한 타입을 제한하는 클래스이다.
* 상태(State)를 표현할 때 많이 사용한다.
* when과 함께 사용하면 else가 필요 없다.
* 컴파일러가 누락된 상태를 검사해준다.
* 상태별로 다른 데이터를 가질 수 있다.
* 단순한 상수 목록은 Enum을 사용한다.
* API 응답, UI State, MVVM, MVI에서 매우 자주 사용된다.
