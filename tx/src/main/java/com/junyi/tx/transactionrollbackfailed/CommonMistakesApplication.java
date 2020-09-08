package com.junyi.tx.transactionrollbackfailed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 事务生效了，但是不会回滚的情况：
 * 1. 只有异常传播出了标记了 @Transactional 注解的方法，事务才能回滚。如果异常在方法内被catch了，并且没有抛出异常，那么不会回滚
 * 2. 默认情况下，出现 RuntimeException（非受检异常）或 Error 的时候，Spring 才会回滚事务
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

