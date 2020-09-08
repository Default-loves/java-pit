package com.junyi.feignandribbon;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * @time: 2020/8/20 9:27
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Configuration
@EnableFeignClients(basePackages = "com.junyi.feignandribbon")
public class Config {
}
