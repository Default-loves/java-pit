package com.junyi.springpart2.aopfeign;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
@Slf4j
//@Component
public class Wrong2Aspect {

    /** through annotaion @FeignClient, it only cut interface */
    @Before("@within(org.springframework.cloud.openfeign.FeignClient)")
    public void before(JoinPoint pjp) {
        log.info("@within(org.springframework.cloud.openfeign.FeignClient) pjp {}, args:{}", pjp, pjp.getArgs());
    }
}
