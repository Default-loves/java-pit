package com.junyi.tx.transactionpropagation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 如果方法涉及多次数据库操作，并希望将它们作为独立的事务进行提交或回滚，那么我们需要考虑进一步细化配置事务传播方式，也就是 @Transactional 注解的 Propagation 属性。
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

