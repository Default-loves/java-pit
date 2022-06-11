package com.junyi;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @time: 2020/9/29 16:25
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class aaa {

    //循环次数
    private static int LOOP_COUNT = 10000000;
    //线程数量
    private static int THREAD_COUNT = 10;
    //元素数量
    private static int ITEM_COUNT = 10;

    public static void main(String[] args) {
        String s = "小";
        String pattern = ".*" + s + "*";
        boolean matches = "大车场".matches(pattern);
        System.out.println(matches);
        HashMap<String, String> map = new HashMap<>();

    }

    @Test
    public void gooduse() throws InterruptedException {
        HashMap<String, LongAdder> freqs = new HashMap<>(ITEM_COUNT);
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_COUNT);
        forkJoinPool.execute(() -> IntStream.rangeClosed(1, LOOP_COUNT).parallel().forEach(__ -> {
                    String key = "item" + ThreadLocalRandom.current().nextInt(ITEM_COUNT);
                    //利用computeIfAbsent()方法来实例化LongAdder，然后利用LongAdder来进行线程安全计数
                freqs.computeIfAbsent(key, k -> new LongAdder()).increment();
                }
        ));
        forkJoinPool.shutdown();
        forkJoinPool.awaitTermination(1, TimeUnit.HOURS);
        //因为我们的Value是LongAdder而不是Long，所以需要做一次转换才能返回
        Map<String, Long> map = freqs.entrySet().stream()
                .collect(Collectors.toMap(
                        e -> e.getKey(),
                        e -> e.getValue().longValue())
                );
        log.info("{}", map.toString());
        long sum = map.values().stream().mapToLong(t -> t).sum();
        log.info("sum: {}", sum);
    }


}





