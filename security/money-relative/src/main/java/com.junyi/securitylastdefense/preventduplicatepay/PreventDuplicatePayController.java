package com.junyi.securitylastdefense.preventduplicatepay;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 从一开始就需要先有业务订单产生，然后使用相同的业务订单号一直贯穿到最后的资金通路，才能真正避免重复资金操作。
 */
@Slf4j
@RequestMapping("preventduplicatepay")
@RestController
public class PreventDuplicatePayController {

    @GetMapping("wrong")
    public void wrong(@RequestParam("orderId") String orderId) {
        PayChannel.pay(UUID.randomUUID().toString(), "123", new BigDecimal("100"));
    }


    @GetMapping("right")
    public void right(@RequestParam("orderId") String orderId) {
        PayChannel.pay(orderId, "123", new BigDecimal("100"));
    }
}
