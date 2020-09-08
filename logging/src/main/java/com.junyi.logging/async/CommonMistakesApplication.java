package com.junyi.logging.async;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * performance_sync.xml 是同步写入日志数据
 * performance_async.xml 是异步写入日志数据
 */
@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
//        System.setProperty("logging.config", "classpath:com\\junyi\\logging\\async\\performance_sync.xml");
        System.setProperty("logging.config", "classpath:com/junyi/logging/async/performance_sync.xml");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

