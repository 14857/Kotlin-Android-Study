# Kotlin 소개

## Kotlin이란?

Kotlin은 안전성(Safety), 간결성(Conciseness), 상호 운용성(Interoperability)을 강조하는 다중 패러다임(Multi-Paradigm), 다중 플랫폼(Multi-Platform) 프로그래밍 언어이다.

JetBrains에서 개발했으며 2010년 하반기에 처음 공개되었다. 이후 지속적인 발전을 거쳐 2016년 2월 첫 정식 버전이 릴리스되었다.

특히 2017년 Google이 Android의 공식 지원 언어로 Kotlin을 채택하면서 Android 개발의 핵심 언어로 자리 잡았다.

처음에는 Java의 더 나은 대안을 제공하기 위해 만들어졌지만, 현재는 JVM뿐 아니라 JavaScript, Native 환경까지 지원하는 범용 프로그래밍 언어로 성장했다.

<br>

현재 Kotlin은 다양한 분야에서 활용되고 있다.

* Android 애플리케이션 개발
* 서버 백엔드 개발
* 데스크톱 애플리케이션 개발
* 웹 개발
* 멀티플랫폼 애플리케이션 개발



<br>

# Kotlin의 핵심 특징

Kotlin의 핵심 특징은 다음 다섯 가지로 정리할 수 있다.

1. 안전성(Safety)
2. 다중 패러다임(Multi-Paradigm)
3. 간결성과 표현력(Conciseness)
4. 상호 운용성(Interoperability)
5. 다중 플랫폼(Multi-Platform)

<br>

## 1. 안전성 (Safety)

프로그래밍 언어의 안전성이란 개발자의 실수를 줄이고 오류를 예방할 수 있는 능력을 의미한다.

실제 개발 환경에서 안전성을 높이기 위해서는 어느 정도의 비용이 필요하다. 컴파일러가 더 많은 정보를 분석해야 하고, 프로그램의 올바름을 더 엄격하게 검증해야 하기 때문이다.

Kotlin은 안전성과 생산성 사이의 균형을 맞추는 것을 중요한 설계 목표로 삼았다.

→ **Java보다 더 높은 수준의 안전성을 제공하면서도 개발 생산성을 크게 떨어뜨리지 않는 언어**를 목표로 설계되었다.

<br>

대표적인 안전성 기능은 다음과 같다.

* Null Safety
* Type Inference
* Smart Cast
* 불변성(Immutable) 지원

<br>

### Null Safety

Java에서 가장 흔하게 발생하는 런타임 오류 중 하나는 NullPointerException(NPE)이다.

Kotlin은 타입 시스템 차원에서 null 허용 여부를 구분하여 이러한 오류를 예방한다.

```kotlin
var name: String = "Kotlin"

// 컴파일 오류
name = null
```

null을 허용하려면 명시적으로 `?`를 사용해야 한다.

```kotlin
var name: String? = null
```

<br>

### Type Inference

변수 타입을 명시하지 않아도 컴파일러가 자동으로 타입을 추론한다.

```kotlin
val age = 20
```

→ 컴파일러는 age를 Int 타입으로 판단한다.

<br>

### Smart Cast

타입 검사를 수행한 뒤 별도의 형변환 없이 해당 타입의 멤버를 사용할 수 있다.

```kotlin
if (obj is String) {
    println(obj.length)
}
```

→ 명시적인 캐스팅 코드가 필요 없다.

<br>
<br>

## 2. 다중 패러다임 (Multi-Paradigm)

Kotlin은 하나의 프로그래밍 패러다임에 제한되지 않는다.

객체지향 프로그래밍, 함수형 프로그래밍, 선언형 프로그래밍, 동시성 프로그래밍 등 다양한 패러다임을 지원한다.

<br>

### 객체지향 프로그래밍 (OOP)

클래스와 객체를 중심으로 프로그램을 설계할 수 있다.

```kotlin
class User(
    val name: String
)
```

Kotlin은 클래스, 상속, 인터페이스, 다형성 등 객체지향 프로그래밍의 핵심 요소를 모두 제공한다.

<br>

### 함수형 프로그래밍 (Functional Programming)

함수를 하나의 값처럼 다룰 수 있다.

함수형 프로그래밍에서는 다음과 같은 작업이 가능하다.

* 함수를 변수에 저장
* 함수를 다른 함수에 전달
* 함수를 반환
* 람다 사용
* 불변 객체 활용

```kotlin
val numbers = listOf(1, 2, 3)

val result = numbers.map { it * 2 }
```

함수형 프로그래밍의 장점

* 코드 재사용성 향상
* 추상화 수준 향상
* 테스트 용이성 증가
* 버그 발생 가능성 감소

Java도 Java 8부터 함수형 프로그래밍을 지원하기 시작했지만, Kotlin은 언어 설계 단계부터 함수형 프로그래밍을 고려했기 때문에 더 높은 수준의 표현력을 제공한다.

<br>

### DSL (Domain Specific Language)

Kotlin은 타입 안전성을 유지하면서 DSL을 작성할 수 있다.

DSL은 특정 문제 영역을 해결하기 위해 만들어진 특화된 언어를 의미한다.

대표적인 예시

* Jetpack Compose
* Kotlin HTML
* Exposed

Compose 역시 Kotlin DSL을 활용한 대표적인 사례이다.

```kotlin
Column {
    Text("Hello Kotlin")
}
```

→ XML 없이 UI를 선언적으로 작성할 수 있다.

<br>

### 동시성 프로그래밍

Kotlin은 Coroutine을 통해 강력한 비동기 프로그래밍을 지원한다.

```kotlin
launch {
    delay(1000)
    println("Done")
}
```

Coroutine의 특징

* 적은 비용으로 많은 작업 처리 가능
* 비동기 코드를 순차 코드처럼 작성 가능
* 콜백 지옥(Callback Hell) 감소
* Android에서 생명주기와 함께 사용 가능

<br>
<br>

## 3. 간결성과 표현력 (Conciseness)

프로그래밍 언어에서 간결성은 개발자의 의도를 불필요한 코드 없이 명확하게 표현하는 능력을 의미한다.

Kotlin은 Java에서 반복적으로 작성하던 보일러플레이트(Boilerplate) 코드를 대폭 줄이는 방향으로 설계되었다.

<br>

#### Java 예시

```java
public class User {
    private String name;

    public String getName() {
        return name;
    }
}
```

<br>

#### Kotlin 예시

```kotlin
data class User(
    val name: String
)
```

훨씬 적은 코드로 동일한 기능을 구현할 수 있다.

<br>

### Kotlin이 줄여준 대표적인 코드

* Getter / Setter
* 익명 클래스
* 형변환 코드
* Null 체크 코드
* 위임 코드
* 데이터 클래스 구현 코드

<br>

### 간결성과 가독성의 균형

Kotlin은 무조건 짧은 문법만 추구하지 않는다.

예를 들어 Scala와 달리 임의의 연산자를 새로 정의할 수 없으며 기존 연산자만 오버로딩할 수 있다.

이는 지나친 문법 남용을 방지하고 코드의 가독성을 유지하기 위한 설계 선택이다.

<br>
<br>

## 4. 상호 운용성 (Interoperability)

Kotlin은 독립적으로 존재하는 언어가 아니다.

기존 Java 생태계와 자연스럽게 협력할 수 있도록 설계되었다.

Kotlin 설계 초기부터 Java와의 상호 운용성은 가장 중요한 목표 중 하나였다.

<br>

### Java 코드 활용

Kotlin에서는 기존 Java 라이브러리를 별도의 변환 없이 그대로 사용할 수 있다.

```kotlin
val list = java.util.ArrayList<String>()
```

<br>

### Kotlin 코드 활용

반대로 Kotlin 코드 역시 거의 추가 작업 없이 Java 프로젝트에서 사용할 수 있다.

→ Java와 Kotlin을 하나의 프로젝트에서 함께 사용할 수 있다.

<br>

### JVM 생태계 활용

Kotlin은 JVM 기반 언어이므로 기존 Java 라이브러리를 그대로 활용할 수 있다.

대표 예시

* Spring Framework
* Retrofit
* OkHttp
* JUnit
* Jackson
* Hibernate

<br>

### 플랫폼 간 상호 운용성

Kotlin은 JVM을 넘어 다양한 플랫폼과 상호작용할 수 있다.

* Java
* JavaScript
* C
* C++
* Objective-C
* Swift

이를 통해 기존 플랫폼의 자산을 재사용할 수 있다.

<br>
<br>

## 5. 다중 플랫폼 (Multi-Platform)

초기 Kotlin은 JVM 기반 언어로 시작했다.

하지만 Kotlin이 성장하면서 다양한 플랫폼을 지원하게 되었다.

현재 Kotlin은 하나의 언어로 여러 환경에서 개발할 수 있는 다중 플랫폼 언어로 발전했다.

<br>

### JVM

가장 대표적인 실행 환경이다.

활용 분야

* Android 애플리케이션
* Spring 서버
* 데스크톱 애플리케이션

<br>

### JavaScript

Kotlin 코드를 JavaScript로 변환하여 실행할 수 있다.

활용 분야

* 웹 브라우저
* Node.js 서버
* JavaScript 라이브러리 개발

```text
Kotlin
   ↓
JavaScript 변환
   ↓
브라우저 실행
```

<br>

### Kotlin Native

JVM 없이 네이티브 바이너리로 컴파일할 수 있다.

지원 플랫폼

* macOS
* Windows
* Linux
* iOS

<br>

### Kotlin Multiplatform (KMP)

하나의 Kotlin 코드베이스를 여러 플랫폼에서 공유할 수 있다.

```text
공통 비즈니스 로직
           ↓
 ┌─────────┼─────────┐
 Android   iOS   Desktop
```

장점

* 코드 중복 감소
* 유지보수 비용 감소
* 플랫폼 간 일관성 향상

최근에는 Android와 iOS에서 로직을 공유하는 방식으로 활발하게 사용되고 있다.

<br>


## 정리

Kotlin은 Java의 장점을 유지하면서 생산성과 안정성을 높이기 위해 설계된 현대적인 프로그래밍 언어이다.

Kotlin의 핵심 가치는 다음과 같다.

* 안전성(Safety)
* 다중 패러다임(Multi-Paradigm)
* 간결성과 표현력(Conciseness)
* 상호 운용성(Interoperability)
* 다중 플랫폼(Multi-Platform)

현재 Kotlin은 Android 개발뿐만 아니라 서버, 웹, 데스크톱, iOS까지 지원하는 범용 언어로 발전하고 있으며, 다양한 플랫폼에서 활용되고 있다.

<br>

## 참고

* 코틀린 완벽 가이드
* Kotlin 공식 문서
* Android Developers
* Kotlin in Action
