package com.junyi.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @time: 2020/8/17 11:34
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

public class Item {
    final String name; //商品名
    int remaining = 1000; //库存剩余

    ReentrantLock lock = new ReentrantLock();

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", remaining=" + remaining +
                '}';
    }
}
