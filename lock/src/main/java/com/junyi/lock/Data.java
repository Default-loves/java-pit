package com.junyi.lock;

/**
 * @time: 2020/8/17 10:55
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 * wrong方法中，虽然加了synchronize，能够解决多个进程对同一个实例的并发访问，然而这儿不是1个实例，而是1000000个实例
 * right方法中，定义了一个静态的变量lock，lock变量在多实例是共享的，能够解决多线程的并发问题
 */

public class Data {

    private static int counter = 0;
    private static Object lock = new Object();

    public static int reset() {
        counter = 0;
        return counter;
    }

    public synchronized void wrong() {
        counter++;
    }

    public void right() {
        synchronized (lock) {
            counter++;
        }
    }

    public static int getCounter() {
        return counter;
    }
}
