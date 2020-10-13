package com.junyi.securitylastdefense.prventcouponfarming;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * coupon batch with specific number coupons
 */
@Data
@Builder
public class CouponBatch {
    private long id;
    private AtomicInteger totalCount;
    private AtomicInteger remainCount;
    private BigDecimal amount;
    private String reason;
}
