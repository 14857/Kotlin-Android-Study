Realtime Communication
실시간 통신(Realtime Communication)이란?

실시간 통신은
서버와 사용자 사이에서 데이터가 거의 즉시 전달되는 통신 방식을 의미한다.

예를 들어:

채팅 메시지
항공편 지연 정보
실시간 주식 가격
실시간 좌석 상태

같은 기능들은 모두 실시간 통신 기술을 사용한다.

모바일 앱에서는 상황에 따라 다양한 실시간 통신 기술을 사용한다.

대표적으로:

Push Notification
FCM / APNs
WebSocket

등이 있다.

<br>
실시간 통신이 필요한 이유

일반적인 앱은 사용자가 직접 새로고침을 해야 데이터를 가져온다.

하지만 실시간 서비스에서는:

서버의 변화
상태 변경
새로운 이벤트

를 즉시 사용자에게 전달해야 한다.

예:

카카오톡 메시지 수신
배달 위치 실시간 이동
항공편 지연 알림
실시간 채팅
<br>
실시간 통신의 대표 기술
기술	특징
Push Notification	앱이 꺼져 있어도 알림 가능
FCM / APNs	Android/iOS Push 시스템
WebSocket	서버와 연결 유지 후 실시간 통신
<br>
문서 목록
Push Notification

앱이 종료되어 있어도 사용자에게 알림을 전달하는 기술

학습 내용:

Push Notification 개념
동작 원리
운영체제 기반 알림 구조
특징 및 사용 사례
<br>
FCM / APNs

Android / iOS의 Push Notification 시스템

학습 내용:

FCM
APNs
Device Token
알림 전달 흐름
Android / iOS 차이점
<br>
WebSocket

서버와 앱이 연결을 유지하면서 데이터를 주고받는 기술

학습 내용:

양방향 통신
실시간 데이터 처리
HTTP와 차이점
연결 유지 방식
<br>
Push vs WebSocket

Push Notification과 WebSocket의 차이점 비교

학습 내용:

언제 Push를 사용하는가
언제 WebSocket을 사용하는가
실제 서비스 구조
실무 사용 사례
<br>
모바일 앱에서의 실시간 통신 구조

실제 모바일 앱에서는 하나의 기술만 사용하는 경우가 거의 없다.

예를 들어 항공 앱에서는:

앱 종료 상태 → Push Notification
앱 실행 상태 → WebSocket

을 함께 사용한다.

즉:

앱 종료 상태 → Push 알림
앱 사용 중 → WebSocket 실시간 업데이트

처럼 역할이 나뉜다.

<br>
실시간 통신 공부 포인트
운영체제 역할 이해하기

Push Notification은 단순 앱 기능이 아니라:

Android OS
iOS

가 직접 관리하는 시스템 기능이다.

<br>
연결 유지 여부 이해하기
Push → 연결 유지 안 함
WebSocket → 연결 유지

이 차이를 이해하는 것이 중요하다.

<br>
실시간성과 배터리의 관계

실시간성이 높을수록:

네트워크 사용량 증가
배터리 사용량 증가
서버 부하 증가

문제가 발생할 수 있다.

따라서 서비스 상황에 따라 적절한 기술 선택이 중요하다.

<br>
실무에서 많이 사용하는 서비스 예시
서비스	사용 기술
카카오톡	Push + WebSocket
Discord	Push + WebSocket
배달 앱	Push + 실시간 위치 통신
항공 앱	Push + WebSocket
주식 앱	WebSocket
<br>
정리

실시간 통신은 현대 모바일 앱에서 매우 중요한 기술이다.

특히:

메신저
항공 앱
게임
실시간 거래 앱

같은 서비스에서는 핵심 기술로 사용된다.

실제 서비스에서는:

Push Notification
WebSocket

을 함께 사용하는 경우가 매우 많다.

<br>
