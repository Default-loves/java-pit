package com.junyi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @time: 2020/8/14 21:16
 * @version: 1.0
 * @author: junyi Xu
 * @description: CopyOnWriteArrayList只适用于读多写少的场景，当读很多的时候性能好，而当有大量写的时候性能很差
 */
@SpringBootApplication
public class CopyOnWriteArrayListDemo {
    public static void main(String[] args) {
        SpringApplication.run(CopyOnWriteArrayListDemo.class, args);
    }
}
