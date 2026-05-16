# WebSocket

## WebSocket이란?

WebSocket은 서버와 클라이언트(앱)가 연결을 계속 유지하면서 실시간으로 데이터를 주고받는 통신 기술이다.

일반적인 HTTP 통신은:

```text
요청 → 응답 → 연결 종료
```

방식으로 동작한다.

반면 WebSocket은 **연결 유지 상태**를 유지한 채 데이터를 계속 주고받는다.

> 즉, 서버와 앱이 계속 연결된 상태에서 실시간으로 통신할 수 있는 기술이다.

<br>

## WebSocket의 핵심 특징

| 항목 | 설명 |
|---|---|
| 연결 유지 | O |
| 양방향 통신 | 가능 |
| 실시간성 | 매우 높음 |
| 앱 종료 상태 | 불가능 |
| 사용 목적 | 실시간 데이터 처리 |

<br>

## HTTP와 WebSocket 차이

| 항목 | HTTP | WebSocket |
|---|---|---|
| 연결 방식 | 요청마다 새 연결 | 연결 유지 |
| 통신 방식 | 요청/응답 | 양방향 |
| 실시간성 | 낮음 | 매우 높음 |

<br>

## WebSocket 동작 과정

1. 클라이언트가 서버에 연결 요청
2. 서버와 WebSocket 연결 생성
3. 연결 유지 상태 지속
4. 필요할 때마다 실시간 데이터 전송

<br>

## WebSocket 사용 사례

- 채팅 앱
- 실시간 주식 데이터
- 게임
- 실시간 위치 공유
- 실시간 항공 정보

<br>

## WebSocket 장점

### 매우 빠른 실시간 통신

연결이 이미 유지되고 있기 때문에 데이터를 빠르게 전달할 수 있다.

### 양방향 통신 가능

- 앱 → 서버
- 서버 → 앱
모두 즉시 가능하다.

<br>

## WebSocket 단점

### 연결 유지 비용 발생

연결을 계속 유지해야 하므로 서버 부하 증가, 메모리 사용량 증가 문제가 발생할 수 있다.

### 앱 종료 상태에서는 사용 어려움

앱이 종료되면 연결도 종료된다.
따라서 일반적으로 Push Notification과 함께 사용한다.

<br>

## WebSocket vs Push Notification

| 항목 | WebSocket | Push Notification |
|---|---|---|
| 연결 유지 | O | X |
| 양방향 통신 | 가능 | 불가능 |
| 앱 종료 상태 동작 | 어려움 | 가능 |
| 사용 목적 | 실시간 데이터 | 알림 전달 |

<br>

### Android에서 자주 사용하는 라이브러리

Android에서는 일반적으로 다음 라이브러리를 많이 사용한다.

- OkHttp WebSocket
- Ktor WebSocket

<br>

## WebSocket URL

| 프로토콜 | 설명 |
|---|---|
| ws:// | 일반 WebSocket |
| wss:// | 보안 WebSocket |

실제 서비스에서는 일반적으로 보안 **WebSocket**를 사용한다.

---
<br>

참고

MDN WebSocket Documentation  
OkHttp 공식 문서  
Android Developers  
Kotlin 공식 문법
