package com.junyi.ribbonretry;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableFeignClients(basePackages = "com.junyi.ribbonretry")
public class AutoConfig {
}
