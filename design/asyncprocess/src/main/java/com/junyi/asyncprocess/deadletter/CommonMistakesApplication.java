package com.junyi.asyncprocess.deadletter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The message will retry to consume when fail, when it always fail, the MQ will have plenty of "dead message",
 * so when consume retry for a while, send message to Dead MQ, and other service will consume message from Dead MQ
 */
@SpringBootApplication
public class CommonMistakesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

