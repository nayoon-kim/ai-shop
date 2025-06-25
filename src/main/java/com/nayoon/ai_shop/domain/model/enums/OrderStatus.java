package com.nayoon.ai_shop.domain.model.enums;

public enum OrderStatus {
    PENDING,     // 생성되었지만 아직 결제 안됨
    PAID,        // 결제 완료
    FAILED,      // 결제 실패
    CANCELED,    // 사용자가 취소
    SHIPPED      // 발송됨
}
