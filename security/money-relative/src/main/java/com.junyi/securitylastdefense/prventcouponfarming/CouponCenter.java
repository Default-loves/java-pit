package com.junyi.securitylastdefense.prventcouponfarming;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 在真实的生产级代码中，一定是根据 CouponBatch 在数据库中插入一定量的 Coupon 记录，每一个优惠券都有唯一的 ID，可跟踪、可注销。
 */
@Slf4j
public class CouponCenter {

    AtomicInteger totalSent = new AtomicInteger(0);

    public void sendCoupon(Coupon coupon) {
        if (coupon != null)
            totalSent.incrementAndGet();
    }

    public int getTotalSentCoupon() {
        return totalSent.get();
    }

    public Coupon generateCouponWrong(long userId, BigDecimal amount) {
        return new Coupon(userId, amount);
    }

    public Coupon generateCouponRight(long userId, CouponBatch couponBatch) {
        if (couponBatch.getRemainCount().decrementAndGet() >= 0) {
            return new Coupon(userId, couponBatch.getAmount());
        } else {
            log.info("优惠券批次 {} 剩余优惠券不足", couponBatch.getId());
            return null;
        }
    }

    public CouponBatch generateCouponBatch() {
        CouponBatch couponBatch = CouponBatch.builder()
                .amount(new BigDecimal("100"))
                .id(1L)
                .totalCount(new AtomicInteger(100))
                .remainCount(new AtomicInteger(100))
                .reason("a party")
                .build();
        return couponBatch;
    }
}
