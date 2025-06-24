✅ 1. Login Functionality (Spring Security + JWT)

📌 Goals

- Issue JWT after user authentication
- Maintain login state using JWT
- Authenticate each request via JWT

✅ 2. View All Products / Filter by Category

✔️ API Design
- GET /products → Retrieve all products
- GET /products?category=electronics → Filter by category


✅ 3. Add Brand Filtering

✔️ API Design
- GET /products?brand=BRAND → Filter by brand

Potential N+1 issue when adding brand filtering → resolved using fetch join


✅ 4. Inventory Management

🔥 Concurrency Problem
- If multiple users order a product with only 1 item left, the stock can go negative.
- Must implement safeguards to prevent this.

🛠 Inventory Decrease API

- Scenario
  When 1,000 users attempt to order 100 items at the same time, stock should not go negative.

- Implementation Options
1. Use Redis for distributed lock and reserve stock
2. Process payment
3. if suceed, reserve stock in DB
4. if fail, rollback or recovery queue

- Use @Version for Optimistic Locking in the DB(maybe use this when do 2)
- Use @Transactional with PESSIMISTIC_WRITE

✅ Stock Reservation + Payment Flow
1. User initiates payment
2. Decrease stock first (reserve the item)
3. Concurrency control must be applied during stock decrement (e.g., Redis lock or Optimistic Lock)
4. Send payment request to PG (Payment Gateway)
5. Confirm payment result

- 💰 If successful → proceed

- ❌ If failed → restore stock

  Stock restoration logic should be handled separately

    - Example:
        1. On payment failure
        2. send an event to a stock restoration queue
        3.  A consumer processes the queue and restores stock accordingly

✅ 5. Coupon System

🛠 First-Come-First-Served Coupon Distribution

Scenario
- Only 100 coupons are issued per day.
- Even under high concurrency, only exactly 100 users should receive them.

Solution
- Use atomic operations in Redis (INCR / DECR)
- Requests exceeding the limit will receive a "Sold Out" message

✅ 6. Favorite Brand Feature

🛠 Increasing Like Count

Scenario
- Many users may like brands simultaneously; the count must remain accurate.

Solution
- Cache like counts in Redis
- Periodically sync with the database



✅ 1. 로그인 기능 (Spring Security + JWT)

📌 목표
- 사용자 인증 후 JWT 발급
- JWT를 통해 로그인 상태 유지
- 이후 요청마다 JWT로 인증 처리

✅ 2. 상품 전체 조회 / 카테고리별 조회

✔️ API 설계
- GET /products → 전체 상품 조회
- GET /products?category=electronics → 카테고리 필터

✅ 3. 브랜드 추가
✔️ API 설계
- GET /products?brand=BRAND → 브랜드 필터

brand 추가로 N+1 이슈 발생 가능 -> fetch join 으로 개선

✅ 4. 재고 관리

🔥 동시성 문제 설명
- 동시에 여러 사용자가 재고가 1개 남은 상품을 주문하면 재고가 음수가 될 수 있음.
- 해결 필요.

🛠 재고 감소 API

- 시나리오
100개의 상품을 동시에 1000명이 주문할 수 있을 때, 재고가 음수가 되는 걸 방지해야 함
- 구현 방식
1. Redis + Lua 스크립트로 락 처리
2. DB의 @Version (Optimistic Lock) 활용
3. @Transactional + PESSIMISTIC_WRITE

✅ 재고 선점 결제 흐름
1. 사용자가 결제를 시도함
2. 재고를 먼저 차감 (선점)
3. 재고 수량을 줄이면서 동시에 동시성 제어를 해야 해 (예: Redis 락 or Optimistic Lock)
4. 결제 요청을 PG사에 보냄
5. 결제 결과 확인
- 💰 성공하면 그대로 유지
- ❌ 실패하면 → 재고를 복원

   재고 복원 로직을 별도로 둠
   - 예: 
    1. 결제 실패 이벤트
  2. 재고 복원 큐에 추가
  3. 소비자(consumer)가 재고 복원

✅ 5. 쿠폰

🛠 쿠폰 선착순 발급

시나리오
- 하루에 100명에게만 쿠폰 발급
- 동시에 여러 명이 요청 보내도 정확히 100명만 성공해야 함

해결책
- Redis에서 atomic 연산 (INCR / DECR)
- 실패한 요청은 “선착순 마감” 메시지

✅ 6. 좋아하는 브랜드 표시

🛠 좋아요 수 증가

시나리오
- 실시간으로 좋아요 누르는 사용자가 많아도 정확한 숫자 유지

해결책
- Redis에 좋아요 수 캐싱하고 일정 주기마다 DB 반영