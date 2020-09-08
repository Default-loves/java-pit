package com.junyi.routelimit;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.IntStream;

/**
 * @time: 2020/8/20 10:19
 * @version: 1.0
 * @author: junyi Xu
 * @description: 使用httpClient1处理10个请求，耗时5s，而httpClient2耗时1s
 * PoolingHttpClientConnectionManager的源码中有个参数，defaultMaxPerRoute=2，也就是同一个主机 / 域名的最大并发请求数为 2。
 */
@RestController
@Slf4j
@RequestMapping("routelimit")
public class RouteLimitController {

    static CloseableHttpClient httpClient1;
    static CloseableHttpClient httpClient2;

    static {
        httpClient1 = HttpClients.custom().setConnectionManager(new PoolingHttpClientConnectionManager()).build();
        httpClient2 = HttpClients.custom().setMaxConnPerRoute(10).setMaxConnTotal(20).build();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                httpClient1.close();
            } catch (IOException ex) {
            }
            try {
                httpClient2.close();
            } catch (IOException ex) {
            }
        }));
    }

    private int sendRequest(int count, Supplier<CloseableHttpClient> client) throws InterruptedException {
        AtomicInteger atomicInteger = new AtomicInteger();
        ExecutorService threadPool = Executors.newCachedThreadPool();
        long begin = System.currentTimeMillis();
        IntStream.rangeClosed(1, count).forEach(i -> {
            threadPool.execute(() -> {
                try (CloseableHttpResponse response = client.get().execute(new HttpGet("http://127.0.0.1:8080/routelimit/server"))) {
                    atomicInteger.addAndGet(Integer.parseInt(EntityUtils.toString(response.getEntity())));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        });
        threadPool.shutdown();
        threadPool.awaitTermination(1, TimeUnit.HOURS);
        log.info("发送 {} 次请求，耗时 {} ms", atomicInteger.get(), System.currentTimeMillis() - begin);
        return atomicInteger.get();
    }

    @GetMapping("wrong")
    public int wrong(@RequestParam(value = "count", defaultValue = "10") int count) throws InterruptedException {
        return sendRequest(count, () -> httpClient1);
    }

    @GetMapping("right")
    public int right(@RequestParam(value = "count", defaultValue = "10") int count) throws InterruptedException {
        return sendRequest(count, () -> httpClient2);
    }

    @GetMapping("server")
    public int server() throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        return 1;
    }
}
