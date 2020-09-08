package com.junyi.feignandribbon;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @time: 2020/8/19 21:12
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@FeignClient(name = "client")
public interface Client {
    @PostMapping("feignandribbon/server")
    void server();
}
