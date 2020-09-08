package com.junyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @time: 2020/8/14 17:54
 * @version: 1.0
 * @author: junyi Xu
 * @description: ConcurrentHashMap的正确使用方法，主要是使用其提供的高效的API，提高了4倍的性能
 */
@SpringBootApplication
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        SpringApplication.run(ConcurrentHashMapDemo.class, args);
    }
}
