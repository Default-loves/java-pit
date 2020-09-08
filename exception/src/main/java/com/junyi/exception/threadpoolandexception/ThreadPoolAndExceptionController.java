package com.junyi.exception.threadpoolandexception;

import jodd.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * execute：通过 execute 提交的任务，出现异常会中断线程
 * submit：通过submit提交的程序，在出现异常的时候，不会中断线程，执行任务出现异常之后，异常存到了一个 outcome 字段中，只有在调用 get 方法获取 FutureTask 结果的时候，才会以 ExecutionException 的形式重新抛出异常
 * submitright：通过 Future 获取结果
 */

@RestController
@Slf4j
@RequestMapping("threadpoolandexception")
public class ThreadPoolAndExceptionController {

    // 设置全局的默认未捕获异常处理程序
    static {
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> log.error("Thread {} got exception", thread, throwable));
    }

    @GetMapping("execute")
    public void execute() throws InterruptedException {

        String prefix = "test";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder()
                .setNameFormat(prefix + "%d")
                .setUncaughtExceptionHandler((thread, throwable) -> log.error("ThreadPool {} got exception", thread, throwable))
                .get());
        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.execute(() -> {
            if (i == 5) throw new RuntimeException("error");
            log.info("I'm done : {}", i);
        }));

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    @GetMapping("submit")
    public void submit() throws InterruptedException {

        String prefix = "test";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setNameFormat(prefix + "%d").get());
        IntStream.rangeClosed(1, 10).forEach(i -> threadPool.submit(() -> {
            if (i == 5) throw new RuntimeException("error");
            log.info("I'm done : {}", i);
        }));

        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }

    @GetMapping("submitright")
    public void submitRight() throws InterruptedException {

        String prefix = "test";
        ExecutorService threadPool = Executors.newFixedThreadPool(1, new ThreadFactoryBuilder().setNameFormat(prefix + "%d").get());

        List<Future> tasks = IntStream.rangeClosed(1, 10).mapToObj(i -> threadPool.submit(() -> {
            if (i == 5) throw new RuntimeException("error");
            log.info("I'm done : {}", i);
        })).collect(Collectors.toList());

        tasks.forEach(task -> {
            try {
                task.get();
            } catch (Exception e) {
                log.error("Got exception", e);
            }
        });
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
    }
}
