package com.junyi.springpart2.aopfeign.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This client has url
 * 之前的 AOP 切面竟然失效了，也就是 within(feign.Client+) 无法切入 ClientWithUrl 的调用了
 */
@FeignClient(name = "anotherClient", url = "http://localhost:45678")
public interface ClientWithUrl {

    @GetMapping("/feignaop/server")
    String api();
}
