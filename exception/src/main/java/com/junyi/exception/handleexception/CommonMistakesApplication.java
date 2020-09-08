package com.junyi.exception.handleexception;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 处理异常的一些实践:
 * 1. 不建议在框架层面进行异常的自动、统一处理，尤其不要随意捕获异常。但，框架可以做兜底工作。如果异常上升到最上层逻辑还是无法处理的话，可以以统一的方式进行异常转换，比如通过 @RestControllerAdvice + @ExceptionHandler，来捕获这些“未处理”异常
 * 2. 捕获了异常后不要直接生吞，不管是多么不重要的异常，都应该留下哪怕一个日志信息
 * 3. 在捕获异常后，不要丢弃异常的原始信息
 * 4. 抛出异常时不指定任何消息，比如`throw new RuntimeException();`
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

