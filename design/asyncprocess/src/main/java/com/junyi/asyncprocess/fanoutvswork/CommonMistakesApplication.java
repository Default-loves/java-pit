package com.junyi.asyncprocess.fanoutvswork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Right to use exchange and queue of RabbitMQ
 * 直接交换器DirectExchange会根据 routingKey发送给对应的 Queue
 * 广播交换器FanoutExchange会忽略 routingKey
 *
 */

@SpringBootApplication
public class CommonMistakesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

