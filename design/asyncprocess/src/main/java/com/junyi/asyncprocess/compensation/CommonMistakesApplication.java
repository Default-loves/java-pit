package com.junyi.asyncprocess.compensation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *  Reliability Problem:
 *  When MQ is down, use CompensationJob schedule to finish the service which MQ need to do
 */

@SpringBootApplication
@EnableScheduling
public class CommonMistakesApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

