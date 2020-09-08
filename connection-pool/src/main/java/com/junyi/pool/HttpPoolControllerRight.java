package com.junyi.pool;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2020/8/19 9:09
 * @version: 1.0
 * @author: junyi Xu
 * @description: 对于连接池要复用而不是每次创建
 * 通过使用wrk压测工具，可以发现两者性能相差了很多倍
 * 正确的使用
 */
@Controller
public class HttpPoolControllerRight {

    private static CloseableHttpClient httpClient = null;
    static {
        //当然，也可以把CloseableHttpClient定义为Bean，然后在@PreDestroy标记的方法内close这个HttpClient
        httpClient = HttpClients.custom()
                .setMaxConnPerRoute(1)
                .setMaxConnTotal(1)
                .evictIdleConnections(60, TimeUnit.SECONDS)
                .build();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                httpClient.close();
            } catch (IOException ignored) {
            }
        }));
    }

    @GetMapping("right")
    public String right() {
        try (CloseableHttpResponse response = httpClient.execute(new HttpGet("http://127.0.0.1:45678/httpclientnotreuse/test"))) {
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
