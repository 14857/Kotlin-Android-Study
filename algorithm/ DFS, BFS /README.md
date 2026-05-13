# DFS / BFS

## DFS / BFS 알고리즘이란?

DFS와 BFS는  
그래프(Graph)를 탐색하기 위한 대표적인 알고리즘이다.

여기서 탐색(Search)이란,
많은 데이터 중에서 원하는 데이터를 찾아가는 과정을 의미한다.

코딩 테스트에서는 다음과 같은 자료구조에서 탐색 문제가 자주 등장한다.

- 그래프(Graph)
- 트리(Tree)
- 2차원 배열(미로 탐색 등)

DFS와 BFS는 탐색 문제의 가장 기본이 되는 알고리즘이며,  
코딩 테스트에서 매우 자주 등장한다.

<br>

## DFS와 BFS를 이해하기 전에 필요한 자료구조

DFS와 BFS를 제대로 이해하려면  
스택(Stack)과 큐(Queue)를 알아야 한다.

자료구조(Data Structure)는 데이터를 저장하고 관리하기 위한 구조를 의미한다.

대표적인 기본 연산은 다음과 같다.

- Push → 데이터 삽입
- Pop → 데이터 삭제

#

### 오버플로(Overflow)

자료구조가 가득 찬 상태에서 데이터를 추가하려 할 때 발생한다.

#

### 언더플로(Underflow)

자료구조가 비어 있는 상태에서 데이터를 삭제하려 할 때 발생한다.

<br>

# 스택(Stack)

스택은 박스를 쌓는 구조와 비슷하다.

가장 나중에 들어온 데이터가 가장 먼저 나간다.

즉,

> 후입선출(LIFO : Last In First Out)

구조를 가진다.

Kotlin에서는 `MutableList`를 이용해 쉽게 구현할 수 있다.

```kotlin
val stack = mutableListOf<Int>()

stack.add(1)
stack.add(2)
stack.add(3)

println(stack.removeLast())
```

#

## 스택 동작 과정

1. 1 삽입
2. 2 삽입
3. 3 삽입
4. 가장 마지막 값인 3 제거

<br>

# 큐(Queue)

큐는 대기 줄과 비슷한 구조이다.

가장 먼저 들어온 데이터가 가장 먼저 나간다.

즉,

> 선입선출(FIFO : First In First Out)

구조를 가진다.

Kotlin에서는 `ArrayDeque`를 많이 사용한다.

```kotlin
val queue = ArrayDeque<Int>()

queue.addLast(1)
queue.addLast(2)
queue.addLast(3)

println(queue.removeFirst())
```

#

## 큐 동작 과정

1. 1 삽입
2. 2 삽입
3. 3 삽입
4. 가장 먼저 들어온 1 제거

<br>

# 재귀 함수(Recursive Function)

재귀 함수는 자기 자신을 다시 호출하는 함수를 의미한다.

DFS 구현에서 매우 자주 사용된다.

재귀 함수를 사용할 때는 반드시 종료 조건이 필요하다.

```kotlin
fun recursive(n: Int) {
    if (n == 0) return

    println(n)
    recursive(n - 1)
}
```

#

## 재귀 함수와 스택

컴퓨터 내부에서 재귀 함수는 스택 구조를 사용한다.

함수가 호출될 때마다 스택에 쌓이고,
함수가 종료되면 스택에서 제거된다.

따라서 DFS와 재귀는 매우 밀접한 관계가 있다.

<br>

# DFS (Depth-First Search)

DFS는 깊이 우선 탐색이라고 부른다.

즉,

> 가능한 깊게 먼저 탐색하는 알고리즘

이다.

특정 노드에서 시작해서 한 방향으로 끝까지 탐색한 뒤,
더 이상 갈 수 없으면 다시 돌아와 다른 방향을 탐색한다.

DFS는 보통 스택(Stack) 또는 재귀 함수로 구현한다.

<br>

## DFS 동작 과정

1. 시작 노드를 방문 처리한다.
2. 방문하지 않은 인접 노드로 이동한다.
3. 더 이상 이동할 수 없으면 이전 노드로 돌아간다.
4. 모든 노드를 방문할 때까지 반복한다.

<br>

## DFS 예시 코드 (재귀)

```kotlin
val graph = arrayOf(
    intArrayOf(),
    intArrayOf(2, 3, 8),
    intArrayOf(1, 7),
    intArrayOf(1, 4, 5),
    intArrayOf(3, 5),
    intArrayOf(3, 4),
    intArrayOf(7),
    intArrayOf(2, 6, 8),
    intArrayOf(1, 7)
)

val visited = BooleanArray(9)

fun dfs(v: Int) {
    visited[v] = true
    print("$v ")

    for (i in graph[v]) {
        if (!visited[i]) {
            dfs(i)
        }
    }
}
```

#

## DFS 특징

- 깊게 탐색한다.
- 재귀로 구현하기 쉽다.
- 스택 구조를 사용한다.
- 경로 탐색 문제에서 자주 사용된다.

<br>

# BFS (Breadth-First Search)

BFS는 너비 우선 탐색이라고 부른다.

즉,

> 가까운 노드부터 탐색하는 알고리즘

이다.

BFS는 큐(Queue)를 이용하여 구현한다.

현재 노드와 가까운 노드를 먼저 방문하기 때문에
최단 거리 문제에서 자주 사용된다.

<br>

## BFS 동작 과정

1. 시작 노드를 큐에 삽입한다.
2. 큐에서 노드를 하나 꺼낸다.
3. 방문하지 않은 인접 노드를 모두 큐에 삽입한다.
4. 큐가 빌 때까지 반복한다.

<br>

## BFS 예시 코드

```kotlin
val graph = arrayOf(
    intArrayOf(),
    intArrayOf(2, 3, 8),
    intArrayOf(1, 7),
    intArrayOf(1, 4, 5),
    intArrayOf(3, 5),
    intArrayOf(3, 4),
    intArrayOf(7),
    intArrayOf(2, 6, 8),
    intArrayOf(1, 7)
)

val visited = BooleanArray(9)

fun bfs(start: Int) {
    val queue: ArrayDeque<Int> = ArrayDeque()

    queue.add(start)
    visited[start] = true

    while (queue.isNotEmpty()) {
        val v = queue.removeFirst()
        print("$v ")

        for (i in graph[v]) {
            if (!visited[i]) {
                visited[i] = true
                queue.addLast(i)
            }
        }
    }
}
```

#

## BFS 특징

- 가까운 노드부터 탐색한다.
- 큐 자료구조를 사용한다.
- 최단 거리 문제에 자주 사용된다.
- 미로 탐색 문제에서 매우 많이 사용된다.

<br>

# 그래프 표현 방식

그래프는 보통 두 가지 방식으로 표현한다.

<br>

## 인접 행렬 (Adjacency Matrix)

2차원 배열을 이용하여 연결 관계를 표현하는 방식이다.

```kotlin
val graph = arrayOf(
    intArrayOf(0, 1, 0),
    intArrayOf(1, 0, 1),
    intArrayOf(0, 1, 0)
)
```

#

### 특징

- 연결 여부 확인이 빠르다.
- 메모리 사용량이 많다.

<br>

## 인접 리스트 (Adjacency List)

연결된 노드만 저장하는 방식이다.

```kotlin
val graph = Array(4) { mutableListOf<Int>() }

graph[1].add(2)
graph[1].add(3)
graph[2].add(1)
```

#

### 특징

- 메모리를 효율적으로 사용한다.
- 연결 여부 확인은 느릴 수 있다.

<br>

# DFS vs BFS 비교

| 항목 | DFS | BFS |
|---|---|---|
| 탐색 방식 | 깊이 우선 | 너비 우선 |
| 사용 자료구조 | 스택 | 큐 |
| 구현 방식 | 재귀/스택 | 큐 |
| 특징 | 깊게 탐색 | 가까운 노드부터 탐색 |
| 활용 | 경로 탐색 | 최단 거리 |

<br>

# Kotlin에서 자주 사용하는 문법

## 방문 배열

```kotlin
val visited = BooleanArray(10)
```

#

## 큐 생성

```kotlin
val queue: ArrayDeque<Int> = ArrayDeque()
```

#

## 리스트 생성

```kotlin
val list = mutableListOf<Int>()
```

#

## 2차원 배열 생성

```kotlin
val board = Array(5) { IntArray(5) }
```

#

## 범위 체크

```kotlin
if (x in 0 until n && y in 0 until m) {

}
```

<br>

# DFS / BFS 문제 풀이 팁

## 방문 처리 먼저 하기

방문 체크를 하지 않으면
무한 반복이 발생할 수 있다.

#

## 그래프 구조 먼저 그려보기

직접 노드 연결 구조를 그려보면 이해가 훨씬 쉽다.

#

## DFS와 BFS 차이 익숙해지기

- DFS → 깊게 탐색
- BFS → 가까운 곳부터 탐색

이 차이를 확실히 이해하는 것이 중요하다.

#

## BFS는 최단 거리 문제에 자주 사용

특히 미로 탐색 문제에서는 BFS가 매우 자주 등장한다.

<br>
---
<br>

참고

한빛미디어 《이것이 코딩 테스트다 with 파이썬》  
Kotlin 공식 문법
