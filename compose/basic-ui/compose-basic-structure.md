# Compose 기본 구조

```kotlin
setContent {
    Chagok_0226Theme {

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            Greeting(
                name = "Android",
                modifier = Modifier.padding(innerPadding)
            )

        }
    }
}
```

<br>

## Compose 기본 흐름

Compose는 XML 대신 코틀린 코드로 화면(UI)을 그리는 방식이다.

기본 흐름은 보통 이렇게 이해하면 쉽다.

```text
setContent
    └ Theme 적용
        └ Scaffold로 화면 구조 생성
            └ 실제 화면 UI 배치
```

- `setContent`
    → Compose 시작
    
- `Theme`
    → 앱 디자인 설정
    
- `Scaffold`
    → 화면 뼈대 구성
    
- 실제 Composable
    → 화면 내용 작성

<br>

## setContent { }

### setContent란?

```kotlin
setContent { }
```
"이 Activity 화면을 Compose로 그리겠다" 라는 선언이다.



```kotlin
setContentView(R.layout.activity_main)
```
예전 Android View 방식에서는 위처럼 XML 레이아웃을 연결했다.

하지만 Compose에서는 XML 대신 중괄호 내부에 코틀린 코드로 UI를 작성한다.


<br>

## Compose UI 시작 지점

Compose에서는 보통 Activity의 `onCreate()` 내부에서 시작한다.

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
        Text("Hello")
    }
}
```

즉, `setContent`는 Compose UI의 시작점이라고 보면 된다.

<br>

## setContent 내부는 Composable 영역

`setContent { }` 안에서는 Composable 함수들을 사용할 수 있다.

예시:

```kotlin
setContent {
    Text("Hello")
}
```

```kotlin
setContent {
    Column {
        Text("A")
        Text("B")
    }
}
```

Compose는 이 내부 코드를 읽어서 화면을 그린다.

<br>

## Theme

```kotlin
Chagok_0226Theme {

}
```

### Theme란?

앱의 전체 디자인 설정을 적용하는 부분이다.

보통 다음 요소들을 관리한다.

- 색상(Color)
- Typography(폰트)
- Shape(모서리 둥글기)
- 다크모드
- Material3 스타일

<br>

### 왜 필요한가?

Theme를 사용하면 앱 전체 디자인을 통일할 수 있다.

```kotlin
MaterialTheme.colorScheme.primary
```

위 처럼 같은 값을 어디서든 동일하게 사용할 수 있다.

<br>

### MaterialTheme

Compose는 Material Design 기반으로 동작한다.

보통 내부적으로 이런 구조를 사용한다.

```kotlin
MaterialTheme(
    colorScheme = ...,
    typography = ...,
    shapes = ...
)
```
Theme는 앱의 디자인 시스템이라고 생각하면 쉽다.

<br>

## 3️⃣ Scaffold()

### Scaffold란?

```kotlin
Scaffold {

}
```

Compose에서 가장 많이 사용하는 화면 구조 컨테이너이다.

"기본 화면 뼈대" 역할을 한다.

<br>

### Scaffold가 제공하는 기능

Scaffold 안에서 아래와 같은 UI들을 쉽게 배치해 화면의 공통 구조를 쉽게 만들 수 있게 도와준다.

- TopAppBar
- BottomBar
- NavigationBar
- FloatingActionButton
- Snackbar
- Drawer

<br>

## 예시

```kotlin
Scaffold(
    topBar = {
        TopAppBar(
            title = {
                Text("홈")
            }
        )
    }
) { innerPadding ->

}
```

<br>

### 왜 많이 사용하는가?

Scaffold를 사용하면:

- 시스템 바 처리
- 패딩 계산
- 화면 구조 분리

같은 작업들을 자동으로 처리해준다.

그래서 대부분의 Compose 화면은 Scaffold 기반으로 작성한다.

<br>

## Modifier

### Modifier란?

Compose에서 UI를 꾸미거나 동작을 추가하는 객체이다.

예전 XML의 다음과 같은 역할들을 담당한다.

- layout_width
- padding
- margin
- click
- background


<br>

### 대표적인 Modifier

#### 크기 지정

```kotlin
Modifier.fillMaxSize()
```

가능한 최대 크기로 채운다.

<br>

```kotlin
Modifier.fillMaxWidth()
```

가로를 꽉 채운다.

<br>

```kotlin
Modifier.size(100.dp)
```

고정 크기 지정

<br>

#

### padding

```kotlin
Modifier.padding(16.dp)
```

내부 여백 추가

#

### background

```kotlin
Modifier.background(Color.Red)
```

배경색 지정

<br>

## click 이벤트

```kotlin
Modifier.clickable {

}
```

클릭 이벤트 추가

<br>

## Modifier 특징

Modifier는 체이닝 방식으로 연결 가능하다.

```kotlin
Modifier
    .fillMaxWidth()
    .padding(16.dp)
    .background(Color.Gray)
```

위에서 아래 순서대로 적용된다.

<br>

## fillMaxSize()

```kotlin
modifier = Modifier.fillMaxSize()
```

화면 전체를 꽉 채우라는 의미이다.
(가능한 최대 width + height 사용)

Scaffold에서 자주 사용된다.

#
<br>

## innerPadding

### innerPadding이란?

Scaffold는 내부에:

- TopBar
- BottomBar
- NavigationBar

같은 UI가 있으면 자동으로 안전한 여백을 계산한다.

그리고 그 값을:

```kotlin
{ innerPadding -> }
```

으로 전달한다.

<br>

### 왜 필요한가?

예를 들어 TopAppBar가 있는데 padding 없이 UI를 배치하면:

```text
TopAppBar와 내용이 겹칠 수 있음
```

그래서 Scaffold가 자동으로:

```text
"이 정도 아래로 내려서 그려"
```

라는 padding 값을 계산해준다.

<br>

### 사용 방법

```kotlin
modifier = Modifier.padding(innerPadding)
```

이렇게 적용한다.

<br>

### 실제 흐름

```text
Scaffold
    ↓
시스템 UI 크기 계산
    ↓
innerPadding 생성
    ↓
content에 전달
    ↓
padding 적용
```


<br>

# Compose 기본 구조 핵심 정리

| 구성 요소 | 역할 |
|---|---|
| setContent | Compose UI 시작 |
| Theme | 앱 디자인 설정 |
| Scaffold | 화면 기본 구조 |
| Modifier | UI 꾸미기 및 동작 추가 |
| fillMaxSize | 화면 전체 크기 사용 |
| innerPadding | 시스템 UI와 겹치지 않게 여백 처리 |

<br>

# 가장 많이 보는 형태

```kotlin
setContent {
    MyTheme {

        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->

            Screen(
                modifier = Modifier.padding(innerPadding)
            )

        }
    }
}
```

이 구조는 Compose에서 거의 기본 템플릿처럼 사용된다.
