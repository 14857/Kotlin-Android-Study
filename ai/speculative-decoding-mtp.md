# MTP (Multi-Token Prediction) / Speculative Decoding

## 왜 나온 기능인가?

LLM(Gemma 같은 모델)은 원래:

```text
1토큰 생성 → 다음 토큰 계산 → 또 생성 → 반복
```

방식으로 동작한다.

문제는 이 과정이 생각보다 느리다는 점이다.

특히 모바일 환경이나 온디바이스 AI에서는:

- GPU/NPU 성능 제한
- 메모리 제한
- 발열 문제

때문에 응답 속도가 더 중요해진다.

그래서 나온 최적화 방식 중 하나가 Speculative Decoding 이다.

Gemma 4에서는 이것을 위한 기능(MTP)이 들어가 있다.

<br>

## MTP란?

MTP(Multi-Token Prediction)로 여러 토큰을 미리 예측하고,
Speculative Decoding은 그 예측을 빠르게 검증해서
LLM 생성 속도를 높이는 최적화 기술이다.

```text
토큰을 1개씩 생성하지 않고
여러 개를 한 번에 예측하려는 방식
```


기존 방식:

```text
"안"
→ "녕"
→ "하"
→ "세"
→ "요"
```

를 하나씩 생성했다면,

MTP는:

```text
"녕하세요"
```

처럼 여러 토큰을 미리 예측하려고 시도한다.

<br>

## Speculative Decoding과 비교하기

| 개념 | 설명 |
|---|---|
| MTP | 여러 토큰을 예측하는 구조 |
| Speculative Decoding | 미리 예측한 토큰을 빠르게 검증하면서 생성 속도를 높이는 기법 |

보통 [MTP 기반 speculative decoding] 형태로 같이 설명된다.

<br>
<br>

# 동작 방식

## 기존 생성 방식

```text
1개 생성
→ 검증
→ 다음 계산
→ 또 생성
```

매 단계마다 모델 전체 계산이 들어가기 때문에 느리다


<br>

## Speculative Decoding 방식

작은 예측 단계:

```text
"안녕하세요"
```

를 한 번에 예상한다.

그 다음 실제 모델이:

```text
정말 맞는지 빠르게 검사
```

한다.

맞으면:

```text
한 번에 여러 토큰 확정
```

된다.

결과:

- GPU 호출 감소
- 모델 계산 감소
- 응답 속도 향상

이 가능해진다.

<br>

## 왜 빨라지는가?

LLM에서 가장 느린 부분은:

```text
매 토큰마다 반복되는 모델 추론
```

이다.

Speculative Decoding은:

```text
한 번의 추론으로 여러 토큰 처리
```

를 시도한다.

그래서:

- latency 감소
- token/sec 증가
- 첫 응답 속도 개선

효과가 생긴다.

## 코드 의미 해석

```kotlin
@OptIn(ExperimentalApi::class)
ExperimentalFlags.enableSpeculativeDecoding = true
```

의 의미는:

```text
Gemma 엔진에서 speculative decoding 기능 활성화
→ MTP 기반 빠른 토큰 생성 모드 ON
```

<br>
<br>

# 왜 Experimental인가?

아직 완전히 안정화된 기능이 아니기 때문이다.

그래서:

```kotlin
@OptIn(ExperimentalApi::class)
```

를 붙여야 한다.

보통 Experimental 기능은:

- 버그 가능성
- 모델별 성능 차이
- 기기별 차이
- 메모리 사용 증가 가능성

등이 있다.

## 실제 체감 효과

환경에 따라 다르지만 보통:

- 응답 시작 속도 개선
- 긴 문장 생성 속도 증가
- token/sec 증가

효과가 있다.

특히:

- Gemma 4B
- 모바일 GPU
- 온디바이스 추론

환경에서 의미가 크다.

<br>
<br>

## 단점

### 1. 메모리 사용 증가 가능성

여러 토큰 예측 정보를 유지해야 한다.

그래서 RAM 사용량이 약간 증가할 수 있다.

### 2. 항상 빨라지는 건 아니다

짧은 응답에서는 차이가 거의 없을 수 있다.

### 3. 기기마다 차이 큼

NPU/GPU 드라이버 영향도 받는다.

<br>

## 온디바이스 AI에서 중요한 이유

모바일 AI는:

```text
속도 vs 배터리 vs 발열
```

싸움이다.

Speculative Decoding은:

```text
같은 계산으로 더 많은 토큰 생성
```

을 목표로 하기 때문에 매우 중요하다.

최근 LLM 엔진들이 거의 다 연구 중인 분야다.

예:

- Gemma
- Llama.cpp
- vLLM
- TensorRT-LLM
- Ollama 일부 백엔드

등에서도 비슷한 최적화가 사용된다.



### 같이 공부하면 좋은 내용

- KV Cache
- Tokenization
- Temperature / TopK / TopP
- Streaming Generation
- Quantization (INT4, INT8)
- GPU Delegate / NPU Delegate
- Prefill / Decode 단계 차이
- LLM 추론 과정
