✅ 1. 로그인 기능 (Spring Security + JWT)

📌 목표
사용자 인증 후 JWT 발급
JWT를 통해 로그인 상태 유지
이후 요청마다 JWT로 인증 처리

✅ 2. 상품 전체 조회 / 카테고리별 조회

✔️ API 설계
GET /products → 전체 상품 조회
GET /products?category=electronics → 카테고리 필터

✅ 3. 상품 구매 → 재고 감소 API

🔥 동시성 문제 설명
동시에 여러 사용자가 재고가 1개 남은 상품을 주문하면 재고가 음수가 될 수 있음.
해결 필요.

