# Button

## Button이란?

`Button`은 사용자의 클릭 입력을 처리하는 가장 기본적인 Compose 컴포넌트이다.  
앱에서 로그인, 저장, 이동 같은 동작을 실행할 때 사용한다.

Compose에서는 버튼도 함수처럼 작성한다.

<br>

## 기본 구조

```kotlin
Button(
    onClick = { }
) {
    Text("버튼")
}
```

### 구성 요소

| 요소 | 설명 |
|---|---|
| `onClick` | 버튼 클릭 시 실행되는 코드 |
| `{ }` 내부 | 버튼 UI 내용 |
| `Text()` | 버튼 안에 표시될 텍스트 |

#

### 가장 기본적인 Button

```kotlin
Button(
    onClick = {
        println("버튼 클릭")
    }
) {
    Text("클릭")
}
```

버튼을 누르면 `onClick` 내부 코드가 실행된다.

<br>

## Modifier 적용

Button에도 Modifier를 사용할 수 있다.

```kotlin
Button(
    onClick = { },
    modifier = Modifier.fillMaxWidth()
) {
    Text("전체 너비 버튼")
}
```

### 자주 사용하는 Modifier

| Modifier | 설명 |
|---|---|
| `fillMaxWidth()` | 가로 전체 차지 |
| `padding()` | 바깥 여백 |
| `size()` | 크기 지정 |
| `height()` | 높이 지정 |

<br>

### 버튼 비활성화

`enabled = false`를 사용하면 버튼을 누를 수 없게 된다.

```kotlin
Button(
    onClick = { },
    enabled = false
) {
    Text("비활성화")
}
```


<br>

## 버튼 색상 변경

`colors`를 사용해서 버튼 색상을 변경할 수 있다.

```kotlin
Button(
    onClick = { },
    colors = ButtonDefaults.buttonColors(
        containerColor = Color.Black,
        contentColor = Color.White
    )
) {
    Text("검정 버튼")
}
```

### 주요 속성

| 속성 | 설명 |
|---|---|
| `containerColor` | 버튼 배경색 |
| `contentColor` | 내부 텍스트 색 |

<br>

## 버튼 모양 변경

```kotlin
Button(
    onClick = { },
    shape = RoundedCornerShape(16.dp)
) {
    Text("둥근 버튼")
}
```

`shape`를 통해 버튼 모서리를 변경할 수 있다.

<br>

## 아이콘이 있는 버튼

```kotlin
Button(
    onClick = { }
) {
    Icon(
        imageVector = Icons.Default.Favorite,
        contentDescription = null
    )

    Spacer(modifier = Modifier.width(8.dp))

    Text("좋아요")
}
```

### 여기서 중요한 점

- Button 내부에는 여러 Composable을 넣을 수 있다.
- `Spacer()`로 간격을 줄 수 있다.

<br>

## OutlinedButton

테두리만 있는 버튼이다.

```kotlin
OutlinedButton(
    onClick = { }
) {
    Text("Outlined")
}
```

<br>

## TextButton

배경 없이 텍스트만 보이는 버튼이다.

```kotlin
TextButton(
    onClick = { }
) {
    Text("Text Button")
}
```

주로:
- 다이얼로그
- 취소 버튼
- 보조 액션

등에서 많이 사용한다.

<br>

## Button 정리

| 버튼 종류 | 특징 |
|---|---|
| `Button` | 기본 버튼 |
| `OutlinedButton` | 테두리 버튼 |
| `TextButton` | 텍스트만 있는 버튼 |

**---**

### 실무에서 자주 사용하는 형태

```kotlin
Button(
    onClick = { },
    modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
    shape = RoundedCornerShape(12.dp)
) {
    Text("로그인")
}
```

로그인/회원가입 버튼에서 자주 사용하는 형태다.

<br>

### 같이 알아두면 좋은 것

다음 개념들과 자주 함께 사용된다.

- `Modifier`
- `Row`
- `Column`
- `Spacer`
- `Icon`
- `State`
- `onClick`
