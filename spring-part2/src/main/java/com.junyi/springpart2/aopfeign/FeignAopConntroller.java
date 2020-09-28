package com.junyi.springpart2.aopfeign;

import com.junyi.springpart2.aopfeign.feign.Client;
import com.junyi.springpart2.aopfeign.feign.ClientWithUrl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 调用 Client 后 AOP 有日志输出，调用 ClientWithUrl 后却没有
 * 原因是，定义了 FeignClient 的 URL 属性后，我们获取的是 LoadBalancerFeignClient 的 delegate ApacheHttpClient，ApacheHttpClient是 new 出来的，不是 Bean
 * 而Spring 只能切入由自己管理的 Bean。
 */
@Slf4j
@RequestMapping("feignaop")
@RestController
public class FeignAopConntroller {

    @Autowired
    private Client client;

    @Autowired
    private ClientWithUrl clientWithUrl;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("client")
    public String client() {
        return client.api();
    }

    @GetMapping("clientWithUrl")
    public String clientWithUrl() {
        return clientWithUrl.api();
    }

    @GetMapping("server")
    public String server() {
        return "OK";
    }
}
