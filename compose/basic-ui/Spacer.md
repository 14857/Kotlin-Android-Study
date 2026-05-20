# Spacer

## Spacer란?

Compose에서 UI 요소 사이의 빈 공간을 만들 때 사용하는 컴포저블이다.

주로:

- 요소 간 간격 조절
- 레이아웃 정리
- 여백 구성

목적으로 사용한다.

<br>

## 기본 사용법

```kotlin
Spacer(modifier = Modifier.height(16.dp))
```

높이 16dp 만큼의 빈 공간이 생성된다.

---

### 세로 간격 만들기

```kotlin
Column {
    Text("첫 번째")

    Spacer(modifier = Modifier.height(16.dp))

    Text("두 번째")
}
```
-> Text 사이에 16dp 간격 생성


### 가로 간격 만들기

```kotlin
Row {
    Text("A")

    Spacer(modifier = Modifier.width(8.dp))

    Text("B")
}
```
-> Text 사이에 가로 간격 생성



## 자주 사용하는 Modifier

#### height()

세로 공간 지정

```kotlin
Modifier.height(20.dp)
```

#### width()

가로 공간 지정

```kotlin
Modifier.width(12.dp)
```

#### weight()

남은 공간을 모두 차지한다.

```kotlin
Spacer(modifier = Modifier.weight(1f))
```

주로 Row, Column 내부에서 사용한다.

#

#### weight() 활용 예시

```kotlin
Row(
    modifier = Modifier.fillMaxWidth()
) {
    Text("왼쪽")

    Spacer(modifier = Modifier.weight(1f))

    Text("오른쪽")
}
```
- Spacer가 가운데 공간을 모두 차지
- 양쪽 Text가 끝으로 배치됨

<br>

## 왜 Spacer를 사용하는가?

Compose에서는 margin 개념이 거의 없기 때문에:

```text
UI 요소 + Spacer + UI 요소
```

형태로 간격을 구성하는 경우가 많다.

즉, Compose에서는 Spacer가 레이아웃 간격 조절의 핵심 역할을 한다.

<br>

### 정리

- Compose에서 빈 공간을 만드는 컴포저블
- UI 요소 사이 간격 조절에 사용
- height(), width()로 크기 지정 가능
- weight()로 남은 공간 차지 가능
- Compose 레이아웃 작성 시 매우 자주 사용됨
