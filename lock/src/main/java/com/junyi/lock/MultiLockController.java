package com.junyi.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @time: 2020/8/17 11:32
 * @version: 1.0
 * @author: junyi Xu
 * @description: 多锁场景导致死锁
 * wrong方法中，成功的次数不是100，其对商品的加锁顺序是随机的，而不是按照特定的顺序，在多线程的环境下会导致死锁
 * right方法中，按照特定的顺序获取锁，因此能够避免死锁，成功次数为100次
 */
@RestController
@RequestMapping("multi-lock")
public class MultiLockController {

    private Logger log = LoggerFactory.getLogger(MultiLockController.class);
    private static HashMap<String, Item> items = new HashMap<>();
    static {
        for (int i = 0; i < 10; i++) {
             String name = "item" + i;
             items.put(name, new Item(name));
        }
    }



    private boolean createOrder(List<Item> order) {
        //存放所有获得的锁
        List<ReentrantLock> locks = new ArrayList<>();

        for (Item item : order) {
            try {
                //获得锁10秒超时
                if (item.lock.tryLock(3, TimeUnit.SECONDS)) {
                    locks.add(item.lock);
                } else {
                    locks.forEach(ReentrantLock::unlock);
                    return false;
                }
            } catch (InterruptedException e) {
            }
        }
        //锁全部拿到之后执行扣减库存业务逻辑
        try {
            order.forEach(item -> item.remaining--);
        } finally {
            locks.forEach(ReentrantLock::unlock);
        }
        return true;
    }

    private List<Item> createCart() {
        return IntStream.rangeClosed(1, 3)
                .mapToObj(i -> "item" + ThreadLocalRandom.current().nextInt(items.size()))
                .map(name -> items.get(name)).collect(Collectors.toList());
    }


    @GetMapping("wrong")
    public long wrong() {
        long begin = System.currentTimeMillis();
        //并发进行100次下单操作，统计成功次数
        long success = IntStream.rangeClosed(1, 100).parallel()
                .mapToObj(i -> {
                    List<Item> cart = createCart();
                    return createOrder(cart);
                })
                .filter(result -> result)
                .count();
        log.info("success:{} totalRemaining:{} took:{}ms items:{}",
                success,
                items.entrySet().stream().map(item -> item.getValue().remaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin, items);

        return success;
    }

    @GetMapping("right")
    public long right() {
        long begin = System.currentTimeMillis();

        long success = IntStream.rangeClosed(1, 100).parallel()
                .mapToObj(i -> {
                    List<Item> cart = createCart().stream()
                            .sorted(Comparator.comparing(Item::getName))
                            .collect(Collectors.toList());
                    return createOrder(cart);
                })
                .filter(result -> result)
                .count();
        log.info("success:{} totalRemaining:{} took:{}ms items:{}",
                success,
                items.entrySet().stream().map(item -> item.getValue().remaining).reduce(0, Integer::sum),
                System.currentTimeMillis() - begin, items);
        return success;
    }
}
