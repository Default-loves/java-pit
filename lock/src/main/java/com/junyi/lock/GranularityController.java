package com.junyi.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @time: 2020/8/17 11:12
 * @version: 1.0
 * @author: junyi Xu
 * @description: 加锁要考虑锁的粒度
 * 更进一步，还需要考虑读写场景和资源访问冲突，考虑使用悲观锁还是乐观锁
 * 对于读写比例不平衡的，使用ReentrantReadWriteLock，即读写锁
 * 对于资源访问冲突比较低的，使用乐观锁，StampedLock
 */



@RestController
@RequestMapping("/granularity/")
public class GranularityController {
    private final List<Integer> data = new ArrayList<>();
    private final Logger log = LoggerFactory.getLogger(GranularityController.class);

    /**
     * 一个慢方法，比如网络或者IO访问等
     */
    private void slow() {
        try {
            TimeUnit.MILLISECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }



    //错误的加锁方法
    @GetMapping("/wrong")
    public long wrong() {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).parallel().forEach(i -> {
            //加锁粒度太粗了，粗粒度
            synchronized (this) {
                slow();
                data.add(i);
            }
        });
        long cost = System.currentTimeMillis() - begin;
        log.info("took time: {}", cost);
        return cost;
    }

    //正确的加锁方法
    @GetMapping("/right")
    public long right() {
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, 1000).parallel().forEach(i -> {
            slow();
            //只对List加锁，细粒度
            synchronized (data) {
                data.add(i);
            }
        });

        long cost = System.currentTimeMillis() - begin;
        log.info("took time: {}", cost);
        return cost;
    }

}
