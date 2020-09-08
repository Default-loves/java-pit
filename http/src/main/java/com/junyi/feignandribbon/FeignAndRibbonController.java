package com.junyi.feignandribbon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.concurrent.TimeUnit;

/**
 * @time: 2020/8/19 21:21
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
@Controller
@RequestMapping("feignandribbon")
public class FeignAndRibbonController {
    @Autowired
    private Client client;

    @GetMapping("client")
    public void timeout() {
        long begin = System.currentTimeMillis();
        try{
            client.server();
        } catch (Exception ex) {
            log.warn("执行耗时：{}ms 错误：{}", System.currentTimeMillis() - begin, ex.getMessage());
        }
    }

    @PostMapping("/server")
    public void server() throws InterruptedException {
        TimeUnit.MINUTES.sleep(10);
    }
}
