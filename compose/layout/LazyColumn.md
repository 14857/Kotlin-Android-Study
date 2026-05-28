# LazyColumn

## LazyColumn이란?

`LazyColumn`은 Compose에서 세로 방향 리스트를 만들 때 사용하는 컴포넌트이다.

기존 Android View 시스템의 `RecyclerView`와 비슷한 역할을 한다.

화면에 보이는 아이템만 렌더링하기 때문에 많은 데이터를 효율적으로 처리할 수 있다.

<br>

## 기본 구조

```kotlin
LazyColumn {

}
```

<br>

## 기본 사용 방법

```kotlin
@Composable
fun UserList() {

    LazyColumn {

        items(5) { index ->

            Text(
                text = "아이템 $index"
            )
        }
    }
}
```

### 결과

```text
아이템 0
아이템 1
아이템 2
아이템 3
아이템 4
```

<br>

## items()

리스트 데이터를 반복해서 화면에 표시할 때 사용한다.

```kotlin
items(개수) { index ->

}
```

또는:

```kotlin
items(list) { item ->

}
```

형태로 사용 가능하다.

<br>

## 리스트 데이터 사용

```kotlin
@Composable
fun UserList() {

    val users = listOf(
        "철수",
        "영희",
        "민수"
    )

    LazyColumn {

        items(users) { user ->

            Text(text = user)
        }
    }
}
```

<br>

## item {}

단일 아이템을 추가할 때 사용한다.

```kotlin
LazyColumn {

    item {
        Text("헤더")
    }

    items(10) { index ->
        Text("아이템 $index")
    }
}
```

<br>

## Modifier와 함께 사용

```kotlin
LazyColumn(
    modifier = Modifier.fillMaxSize()
) {

    items(20) { index ->

        Text(
            text = "아이템 $index",
            modifier = Modifier.padding(16.dp)
        )
    }
}
```

<br>

## 스크롤 가능한 리스트

`LazyColumn`은 기본적으로 스크롤이 가능하다.

아이템 개수가 화면보다 많아지면 자동으로 스크롤된다.

<br>

## rememberLazyListState

리스트의 스크롤 상태를 저장할 수 있다.

```kotlin
val listState = rememberLazyListState()
```

```kotlin
LazyColumn(
    state = listState
) {
}
```

<br>

## 현재 스크롤 위치 확인

`rememberLazyListState()`를 사용하면 현재 리스트가 어디까지 스크롤되었는지 확인할 수 있다.

```kotlin
val listState = rememberLazyListState()
```

이 상태(state)를 `LazyColumn`에 연결한다.

```kotlin
LazyColumn(
    state = listState
) {

    items(100) { index ->

        Text(
            text = "아이템 $index"
        )
    }
}
```

<br>

### firstVisibleItemIndex

현재 화면에 보이는 첫 번째 아이템의 위치(index)를 반환한다.

```kotlin
listState.firstVisibleItemIndex
```

<br>

예를 들어 다음과 같이 화면에 보이면,

```text
아이템 0
아이템 1
아이템 2
```
listState.firstVisibleItemIndex 값은 0이다.

<br>

스크롤해서 다음이 화면에 보이면,

```text
아이템 15
아이템 16
아이템 17
```

listState.firstVisibleItemIndex 값은 15가 된다.

<br>

### 왜 사용할까?

스크롤 위치를 기준으로 UI를 변경할 수 있다.

예시:

- 맨 위로 가기 버튼 표시
- 특정 위치에서 TopBar 변경
- 스크롤 위치 저장
- 무한 스크롤 구현

<br>

### 예제

```kotlin
@Composable
fun MyList() {

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState
    ) {

        items(100) { index ->

            Text(
                text = "아이템 $index",
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
```

<br>

### 추가로 많이 사용하는 값

```kotlin
listState.firstVisibleItemScrollOffset
```

- 현재 아이템 내부에서 얼마나 스크롤되었는지 반환
- 픽셀(px) 단위 값

<br>

## key 사용

리스트 아이템을 안정적으로 관리하기 위해 사용한다.

```kotlin
items(
    items = users,
    key = { user ->
        user.id
    }
) { user ->

}
```

<br>

### key를 사용하는 이유

Compose는 상태(State)를 기반으로 화면을 다시 그린다.

리스트 순서가 변경되면 어떤 아이템이 변경되었는지 정확히 구분해야 한다.

`key`를 사용하면 Compose가 아이템을 안정적으로 추적할 수 있다.

<br>

## RecyclerView와 LazyColumn 차이점

| RecyclerView | LazyColumn |
|---|---|
| Android View 시스템 사용 | Compose UI 사용 |
| XML로 UI 작성 | Composable 함수로 UI 작성 |
| Adapter 필요 | Adapter 없음 |
| ViewHolder 필요 | ViewHolder 없음 |
| findViewById 사용 가능 | 상태(State) 기반 UI |
| 코드 양이 많음 | 코드가 간단함 |
| RecyclerView.Adapter 구현 필요 | items()로 리스트 구성 |
| View 재활용 직접 관리 | Compose가 자동 최적화 |
| 기존 Android 방식 | Compose 방식 |
| 복잡한 구조가 많음 | 선언형 UI 방식 |


<br>

### LazyColumn의 장점

- 코드 양 감소
- XML 필요 없음
- 상태 관리가 쉬움
- Compose와 자연스럽게 연결
- 유지보수 편리
- 선언형 UI 구조 사용

<br>

## LazyColumn 특징

- 세로 방향 리스트
- 필요한 아이템만 렌더링
- RecyclerView 역할 수행
- 성능 최적화 자동 처리
- Compose 방식 UI 구성

<br>

## 자주 같이 사용하는 것

```text
LazyColumn
→ rememberLazyListState
→ key
→ itemsIndexed
→ LazyRow
→ Grid
```

<br>

## 참고

- RecyclerView를 Compose 방식으로 대체하는 컴포넌트
- 데이터가 많은 리스트 화면에서 자주 사용
- 실무에서 가장 많이 사용하는 Compose 컴포넌트 중 하나
