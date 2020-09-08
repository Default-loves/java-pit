package com.junyi.tx.transactionfail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @time: 2020/8/25 13:56
 * @version: 1.0
 * @author: junyi Xu
 * @description: 事务不生效的两个原因
 * 1. 只有定义在 public 方法上的 @Transactional 才能生效。Spring 默认通过动态代理的方式实现 AOP，对目标方法进行增强，private 方法无法代理到，Spring 自然也无法动态增强事务处理逻辑。
 * P.s：对于 private 方法其实也可以生效，需要手动使用AspectJ静态织入AOP
 * 2. 必须通过代理过的类从外部调用目标方法才能生效
 */

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}