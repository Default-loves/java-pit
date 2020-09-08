package com.junyi.executor;

import jodd.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @time: 2020/8/17 14:16
 * @version: 1.0
 * @author: junyi Xu
 * @description: 正確使用线程池
 * 更进一步，由于任务队列满了后，线程池才开始扩大线程数量直至最大线程数，我们可以重写队列的 offer 方法，造成这个队列已满的假象
 * 由于我们 Hack 了队列，在达到了最大线程后势必会触发拒绝策略，实现一个自定义的拒绝策略处理程序，这个时候再把任务真正插入队列
 */
@Controller
public class MyController {

    public static final Logger log = LoggerFactory.getLogger(MyController.class);

    @GetMapping("oom1")
    public void oom1() throws InterruptedException {

        // newFixedThreadPool 和 newCachedThreadPool 都是不建议使用的，一个是无界的任务队列，一个是无界的线程最大数量
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
//        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) Executors.newCachedThreadPool();

        //打印线程池的信息
        printStats(threadPool);
        for (int i = 0; i < 100000000; i++) {
            threadPool.execute(() -> {
                // 创建一个很大的字符串
                String payload = IntStream.rangeClosed(1, 1000000)
                        .mapToObj(__ -> "a")
                        .collect(Collectors.joining("")) + UUID.randomUUID().toString();
                try {
                    TimeUnit.HOURS.sleep(1);
                } catch (InterruptedException e) {
                }
                log.info(payload);
            });
        }

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }


    @GetMapping("right")
    @ResponseBody
    public int right() throws InterruptedException {
        //使用一个计数器跟踪完成的任务数
        AtomicInteger atomicInteger = new AtomicInteger();
        //创建一个具有2个核心线程、5个最大线程，使用容量为10的ArrayBlockingQueue阻塞队列作为工作队列的线程池，使用默认的AbortPolicy拒绝策略
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2, 5,
                5, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                new ThreadFactoryBuilder().setNameFormat("demo-thread-pool-%d").get(),
                new ThreadPoolExecutor.AbortPolicy());

        printStats(threadPool);
        //每隔1秒提交一次，一共提交20次任务
        IntStream.rangeClosed(1, 20).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int id = atomicInteger.incrementAndGet();
            try {
                threadPool.submit(() -> {
                    log.info("{} started", id);
                    //每个任务耗时10秒；
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    log.info("{} finished", id);
                });
            } catch (Exception ex) {
                //提交出现异常的话，打印出错信息并为计数器减一
                log.error("error submitting task {}", id, ex);
                atomicInteger.decrementAndGet();
            }
        });

        TimeUnit.SECONDS.sleep(60);
        return atomicInteger.intValue();
    }

    private void printStats(ThreadPoolExecutor threadPool) {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            log.info("=========================");
            log.info("Pool Size: {}", threadPool.getPoolSize());
            log.info("Active Threads: {}", threadPool.getActiveCount());
            log.info("Number of Tasks Completed: {}", threadPool.getCompletedTaskCount());
            log.info("Number of Tasks in Queue: {}", threadPool.getQueue().size());

            log.info("=========================");
        }, 0, 3, TimeUnit.SECONDS);
    }


    @GetMapping("better")
    public int better() throws InterruptedException {
        //这里开始是激进线程池的实现
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(10) {
            @Override
            public boolean offer(Runnable e) {
                //先返回false，造成队列满的假象，让线程池优先扩容
                return false;
            }
        };

        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                2, 5,
                5, TimeUnit.SECONDS,
                queue, new ThreadFactoryBuilder().setNameFormat("demo-threadpool-%d").get(), (r, executor) -> {
            // 拒绝策略
            try {
                //等出现拒绝后再加入队列
                //如果希望队列满了阻塞线程而不是抛出异常，那么可以注释掉下面三行代码，修改为executor.getQueue().put(r);
                if (!executor.getQueue().offer(r, 0, TimeUnit.SECONDS)) {
                    throw new RejectedExecutionException("ThreadPool queue full, failed to offer " + r.toString());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        //激进线程池实现结束

        printStats(threadPool);
        //每秒提交一个任务，每个任务耗时10秒执行完成，一共提交20个任务

        //任务编号计数器
        AtomicInteger atomicInteger = new AtomicInteger();

        IntStream.rangeClosed(1, 20).forEach(i -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int id = atomicInteger.incrementAndGet();
            try {
                threadPool.submit(() -> {
                    log.info("{} started", id);
                    try {
                        TimeUnit.SECONDS.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    log.info("{} finished", id);
                });
            } catch (Exception ex) {
                log.error("error submitting task {}", id, ex);
                atomicInteger.decrementAndGet();
            }
        });

        TimeUnit.SECONDS.sleep(60);
        return atomicInteger.intValue();
    }
}
