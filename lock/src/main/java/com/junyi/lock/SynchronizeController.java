package com.junyi.lock;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.IntStream;

/**
 * @time: 2020/8/17 10:56
 * @version: 1.0
 * @author: junyi Xu
 * @description: 正确使用synchronize解决并发问题
 * @see Data
 */
@RestController
public class SynchronizeController {

    @GetMapping("wrong")
    public int wrong(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        //多线程循环一定次数调用Data类不同实例的wrong方法
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().wrong());
        return Data.getCounter();
    }

    @GetMapping("right")
    public int right(@RequestParam(value = "count", defaultValue = "1000000") int count) {
        Data.reset();
        IntStream.rangeClosed(1, count).parallel().forEach(i -> new Data().right());
        return Data.getCounter();
    }
}
