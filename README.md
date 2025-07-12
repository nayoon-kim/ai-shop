# 🛒 AI Shopping Mall Project

This is a Spring Boot–based shopping mall project with AI-powered product recommendation features.  
It includes JWT authentication, concurrency control, Redis caching and more.

---

## ✅ Key Features

### 1. 🔐 Login Functionality (Spring Security + JWT)
- Issue JWT after user authentication
- Maintain login session with JWT
- Authenticate every request using JWT

---

### 2. 📦 Product Retrieval
- `GET /products` : Retrieve all products
- `GET /products?category=electronics` : Filter by category
- `GET /products?brand=BRAND` : Filter by brand

---

### 3. 📉 Inventory Management & Concurrency Control

#### 🔥 Problem
- If many users order a product with only 1 item left, the stock may go negative

#### 🛠 Solution
- Reserve stock in Redis before deducting from the database
- Use Optimistic Lock (@Version)
- Use Pessimistic Lock (`@Transactional` + `PESSIMISTIC_WRITE`)

#### 💳 Payment Flow

1. User initiates payment
2. Pre-decrease stock (reservation)
3. Send payment request to the PG provider
4. Confirm payment result
    - ✅ Success: keep the stock deducted
    - ❌ Failure: restore the stock
        - Trigger payment failure event
        - Add to stock rollback queue
        - Consumer processes restoration

---

### 4. 🎟️ Coupon System (planned)

#### 💡 Scenario
- Only 100 users per day can claim coupons
- Even under heavy load, exactly 100 claims should succeed

#### 🔧 Implementation
- Use Redis atomic operations (`INCR` / `DECR`)
- Send "Coupon limit reached" message for failed requests

---

### 5. ❤️ Favorite Brand Feature (planned)

#### 🧠 Problem
- Even with high-frequency "like" clicks, the count must remain accurate

#### 💾 Solution
- Cache like counts in Redis
- Periodically flush counts to the database (via batch or cron job)

---

## ⚙️ Tech Stack

- Java 17, Spring Boot 3
- Spring Security + JWT
- Redis (distributed locking, caching, atomic counters)
- JPA (Optimistic/Pessimistic Locking)
- PostgreSQL
- Kafka (used for stock rollback events and consumers)
- Docker & Docker Compose
- CI/CD with Jenkins (planned)


# 🛒 AI 쇼핑몰 프로젝트

Spring Boot 기반의 AI 추천 기능이 포함된 쇼핑몰 프로젝트입니다.  
JWT 인증, 동시성 제어, Redis 캐싱 등 다양한 백엔드 기능을 구현하였습니다.

---

## ✅ 주요 기능

### 1. 🔐 로그인 기능 (Spring Security + JWT)
- 사용자 인증 후 JWT 발급
- JWT를 통해 로그인 상태 유지
- 모든 요청에 대해 JWT 기반 인증 처리

---

### 2. 📦 상품 조회
- `GET /products` : 전체 상품 조회
- `GET /products?category=electronics` : 카테고리 필터링
- `GET /products?brand=BRAND` : 브랜드 필터링

---

### 3. 📉 재고 관리 및 동시성 제어

#### 🔥 문제 상황
- 재고가 1개인 상품에 대해 여러 사용자가 동시에 주문 시, 재고가 음수가 될 수 있음

#### 🛠 해결 방안
- Redis를 이용한 재고 선점 후, DB에서 최종 차감
- Optimistic Lock (@Version)
- Pessimistic Lock (`@Transactional` + `PESSIMISTIC_WRITE`)

#### 💳 결제 흐름

1. 사용자가 결제 요청
2. 재고 선점 (Redis or DB Lock)
3. PG사 결제 요청
4. 결제 결과 확인
    - ✅ 성공: 재고 차감 유지
    - ❌ 실패: 재고 복원
        - 결제 실패 이벤트 발생
        - 재고 복원 큐에 적재
        - Consumer가 복원 처리

---

### 4. 🎟️ 쿠폰 시스템

#### 💡 시나리오
- 하루 100명에게만 쿠폰 제공
- 동시 요청에도 정확히 100명만 성공해야 함

#### 🔧 구현 방식
- Redis `INCR` 또는 `DECR`로 원자적 제어
- 실패 시 "선착순 마감" 메시지 응답

---

### 5. ❤️ 좋아하는 브랜드 기능

#### 🧠 문제 상황
- 좋아요 버튼이 실시간으로 많이 눌려도 정확한 수 유지 필요

#### 💾 해결 방안
- Redis에 좋아요 수 캐싱
- 주기적으로 DB에 반영 (Batch 처리 또는 Cron 활용)
