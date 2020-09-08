package com.junyi.lock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @time: 2020/8/17 10:56
 * @version: 1.0
 * @author: junyi Xu
 * @description: 锁相关问题
 * 1. 锁粒度问题
 * 2. 多个锁导致死锁问题
 * 3. 正确使用synchronize
 */
@SpringBootApplication
public class LockDemo {
    public static void main(String[] args) {
        SpringApplication.run(LockDemo.class, args);
    }
}
