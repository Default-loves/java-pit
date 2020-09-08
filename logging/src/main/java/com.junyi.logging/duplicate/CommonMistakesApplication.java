package com.junyi.logging.duplicate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 日志重复问题：
 * 1. 自定义的logger默认会继承root，因此会导致重复日志
 * 2. LevelFilter 没有配置 onMatch 和 onDisMatch
 */
@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        System.setProperty("logging.config", "classpath:org/geekbang/time/commonmistakes/logging/duplicate/multiplelevelsfilter.xml");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

