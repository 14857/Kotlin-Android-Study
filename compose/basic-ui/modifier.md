# Modifier

## Modifier란?

Compose에서 UI의 크기, 배치, 여백, 배경, 클릭 이벤트 등을 설정할 때 사용하는 객체이다.

기존 Android View 시스템의:

- LayoutParams
- padding
- margin
- click listener

등의 역할을 Compose에서는 Modifier가 담당한다.

Compose UI를 꾸미고 동작을 추가하는 핵심 요소이다.

#

## 기본 사용법

```kotlin
Text(
    text = "Hello Compose",
    modifier = Modifier.padding(16.dp)
)
```

`modifier` 파라미터를 통해 UI에 다양한 속성을 추가할 수 있다.

#

## Modifier 체이닝

Modifier는 여러 개를 이어서 사용할 수 있다.

```kotlin
Modifier
    .fillMaxWidth()
    .padding(16.dp)
    .background(Color.LightGray)
```

이처럼 여러 Modifier를 연결해서 사용한다. 이를 Modifier 체이닝(Chaining)이라고 한다.

#

## 자주 사용하는 Modifier

### 크기 관련

#### fillMaxSize()

부모 영역 전체를 차지한다.

```kotlin
Modifier.fillMaxSize()
```

#

#### fillMaxWidth()

가로 영역 전체를 차지한다.

```kotlin
Modifier.fillMaxWidth()
```

#

#### size()

고정 크기를 지정한다.

```kotlin
Modifier.size(100.dp)
```

#

### 여백 관련

#### padding()

내부 여백을 추가한다.

```kotlin
Modifier.padding(16.dp)
```

방향별 지정도 가능하다.

```kotlin
Modifier.padding(
    horizontal = 16.dp,
    vertical = 8.dp
)
```

#

### 배경 관련

#### background()

배경색을 지정한다.

```kotlin
Modifier.background(Color.Gray)
```

#

### 클릭 이벤트

#### clickable()

클릭 이벤트를 처리한다.

```kotlin
Modifier.clickable {
    println("클릭됨")
}
```

#

### 정렬 관련

#### align()

부모 Layout 내부에서 정렬 위치를 지정한다.

```kotlin
Modifier.align(Alignment.CenterHorizontally)
```

주로 Column, Box 내부에서 사용한다.

#

## Modifier 순서가 중요한 이유

Modifier는 위에서 아래 순서대로 적용된다.

따라서 순서에 따라 결과가 달라질 수 있다.

#### 예시 1

```kotlin
Modifier
    .background(Color.Red)
    .padding(16.dp)
```

- 먼저 배경 적용
- 이후 padding 적용

→ 배경색이 padding 영역까지 포함된다.

#

#### 예시 2

```kotlin
Modifier
    .padding(16.dp)
    .background(Color.Red)
```

- 먼저 padding 적용
- 이후 배경 적용

→ 배경색이 내부 영역에만 적용된다.

#

## 실전 예시

```kotlin
Button(
    onClick = {},
    modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
) {
    Text("확인")
}
```

- 버튼이 가로 전체 차지
- 바깥쪽 여백 16dp 적용

#
<br>

## 정리

- Compose UI 속성을 설정하는 핵심 객체
- 크기, 배치, 배경, 클릭 이벤트 등을 담당
- 여러 Modifier를 체이닝해서 사용 가능
- 순서에 따라 UI 결과가 달라질 수 있음
- 대부분의 Compose UI에서 필수적으로 사용됨
