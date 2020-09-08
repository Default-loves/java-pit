package com.junyi.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @time: 2020/8/17 14:16
 * @version: 1.0
 * @author: junyi Xu
 * @description: 线程池
 * 1. 根据场景手动创建 ThreadPoolExecutor，而不要使用Executors类下面提供的
 * 2. IO任务和计算任务不能共用同一个线程池，对于不同的服务最好使用自己的线程池，避免相互影响，而不是复用同一个线程池
 * 3. 用一些监控手段来观察线程池的状态
 */
@SpringBootApplication
public class ExecutorPoolDemo {
    public static void main(String[] args) {
        SpringApplication.run(ExecutorPoolDemo.class, args);
    }
}
