# rememberCoroutineScope

## 목차

1. rememberCoroutineScope란?
2. 왜 사용할까?
3. Coroutine이란?
4. 기본 사용법
5. LaunchedEffect와 차이점
6. 실전 예제
7. 차곡 프로젝트 예시
8. 자주 하는 실수
9. 정리

<br>

# rememberCoroutineScope란?

`rememberCoroutineScope()`는 Composable 내부에서 Coroutine을 실행하기 위한 Scope를 생성하는 함수이다.

```kotlin
val scope = rememberCoroutineScope()
```

이렇게 생성한 scope를 사용해서 Coroutine을 실행할 수 있다.

```kotlin
scope.launch {
    // 비동기 작업
}
```

<br>

# 왜 사용할까?

Compose에서는 버튼 클릭과 같은 사용자 이벤트가 자주 발생한다.

예를 들어

* 저장 버튼 클릭
* 삭제 버튼 클릭
* Snackbar 표시
* API 요청

같은 작업은 화면이 시작될 때 자동으로 실행되는 것이 아니라 사용자의 행동에 의해 실행된다.

이때 Coroutine이 필요하다.

예시

```kotlin
Button(
    onClick = {
        snackbarHostState.showSnackbar("저장 완료")
    }
)
```

위 코드는 컴파일 오류가 발생한다.

이유는

```kotlin
showSnackbar()
```

가 suspend 함수이기 때문이다.

suspend 함수는 Coroutine 안에서만 실행할 수 있다.

그래서

```kotlin
val scope = rememberCoroutineScope()

Button(
    onClick = {
        scope.launch {
            snackbarHostState.showSnackbar("저장 완료")
        }
    }
)
```

처럼 Coroutine을 생성해서 실행해야 한다.

<br>

# Coroutine이란?

Coroutine은 쉽게 말하면

"오래 걸리는 작업을 멈추지 않고 처리하기 위한 기능"

이다.

예를 들어 서버 요청이 3초 걸린다고 가정해보자.

```kotlin
fun loadData() {
    Thread.sleep(3000)
}
```

이 코드를 실행하면 화면이 멈춘다.

하지만 Coroutine을 사용하면

```kotlin
scope.launch {
    delay(3000)
}
```

처럼 화면을 멈추지 않고 작업을 실행할 수 있다.

안드로이드에서는

* API 호출
* 데이터베이스 조회
* 파일 저장
* Snackbar 표시

등을 Coroutine으로 처리한다.

<br>

# 기본 사용법

## Scope 생성

```kotlin
val scope = rememberCoroutineScope()
```

왜 remember를 사용할까?

Compose는 Recomposition이 발생하면 함수가 다시 실행된다.

만약 Scope를 계속 새로 만들면 비효율적이다.

그래서 Compose가 기존 Scope를 기억하도록 remember를 사용한다.

<br>

## Coroutine 실행

```kotlin
val scope = rememberCoroutineScope()

Button(
    onClick = {
        scope.launch {
            println("버튼 클릭")
        }
    }
) {
    Text("클릭")
}
```

한 줄씩 해석

```kotlin
val scope = rememberCoroutineScope()
```

Coroutine을 실행할 Scope 생성

```kotlin
scope.launch
```

새로운 Coroutine 시작

```kotlin
{
    println("버튼 클릭")
}
```

Coroutine 내부에서 실행될 코드

<br>

# LaunchedEffect와 차이점

많이 헷갈리는 부분이다.

## LaunchedEffect

```kotlin
LaunchedEffect(Unit) {
    viewModel.loadData()
}
```

화면 진입 시 자동 실행

즉

"Compose가 실행시켜주는 Coroutine"

이다.

<br>

## rememberCoroutineScope

```kotlin
val scope = rememberCoroutineScope()

Button(
    onClick = {
        scope.launch {
            viewModel.loadData()
        }
    }
)
```

사용자 이벤트 발생 시 실행

즉

"개발자가 직접 실행하는 Coroutine"

이다.

정리하면

| 상황         | 사용                     |
| ---------- | ---------------------- |
| 화면 진입 시 실행 | LaunchedEffect         |
| 상태 변경 시 실행 | LaunchedEffect         |
| 버튼 클릭 시 실행 | rememberCoroutineScope |
| 사용자 이벤트 처리 | rememberCoroutineScope |

<br>

# 실전 예제

## Snackbar 표시

```kotlin
@Composable
fun SaveScreen() {

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "저장 완료"
                )
            }
        }
    ) {
        Text("저장")
    }
}
```

왜 Coroutine이 필요할까?

```kotlin
showSnackbar()
```

는 suspend 함수이기 때문이다.

suspend 함수는 Coroutine 내부에서만 실행 가능하다.

<br>

# 차곡 프로젝트 예시

일정 저장 후 Snackbar 표시

```kotlin
@Composable
fun ScheduleScreen(
    viewModel: ScheduleViewModel
) {

    val scope = rememberCoroutineScope()

    Button(
        onClick = {

            viewModel.saveSchedule()

            scope.launch {
                snackbarHostState.showSnackbar(
                    "일정이 저장되었습니다."
                )
            }
        }
    ) {
        Text("저장")
    }
}
```

실제 프로젝트에서도 가장 자주 사용하는 패턴 중 하나다.

<br>

# 자주 하는 실수

## 1. LaunchedEffect로 버튼 클릭 처리

잘못된 예

```kotlin
LaunchedEffect(Unit) {
    saveData()
}
```

화면 진입 시 자동 실행된다.

버튼 클릭과는 관계가 없다.

사용자 이벤트는

```kotlin
rememberCoroutineScope()
```

를 사용한다.

<br>

## 2. suspend 함수를 직접 호출

잘못된 예

```kotlin
Button(
    onClick = {
        snackbarHostState.showSnackbar("저장")
    }
)
```

컴파일 오류 발생

이유

```kotlin
showSnackbar()
```

가 suspend 함수이기 때문이다.

반드시

```kotlin
scope.launch {
    snackbarHostState.showSnackbar("저장")
}
```

처럼 실행해야 한다.

<br>

## 3. Coroutine과 Thread를 같은 것으로 생각

많은 사람들이

```kotlin
Coroutine = Thread
```

라고 생각한다.

하지만 다르다.

Thread는 무겁고 생성 비용이 크다.

Coroutine은 가볍고 수천 개도 생성 가능하다.

그래서 안드로이드에서는 Coroutine을 사용한다.

<br>

# 정리

* rememberCoroutineScope는 Composable에서 Coroutine을 실행하기 위한 Scope를 생성한다.
* 사용자 이벤트 처리 시 주로 사용한다.
* 버튼 클릭, Snackbar 표시, 비동기 작업 실행에 자주 사용한다.
* suspend 함수는 Coroutine 내부에서만 실행할 수 있다.
* 화면 진입 시 자동 실행은 LaunchedEffect를 사용한다.
* 사용자 이벤트 실행은 rememberCoroutineScope를 사용한다.
* 가장 중요한 차이는 "누가 실행시키는가"이다.
* LaunchedEffect는 Compose가 실행한다.
* rememberCoroutineScope는 개발자가 직접 실행한다.
