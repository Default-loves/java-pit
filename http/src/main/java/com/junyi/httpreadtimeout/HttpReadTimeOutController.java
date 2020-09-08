package com.junyi.httpreadtimeout;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Request;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @time: 2020/8/19 17:52
 * @version: 1.0
 * @author: junyi Xu
 * @description: 客户端读取超时为2s，而服务执行需要5s，因此返回给客户端的状态码是500，显示服务器异常，但是最终服务执行5s后悔显示完成
 * 因此读取超时的设置不能只考虑网络环境，还要考虑服务器的执行状态
 *
 * 连接超时参数 ConnectTimeout，让用户配置建连阶段的最长等待时间，一般配置为1~6s，对于内网服务调用，甚至可以更短；
 *
 * 读取超时参数 ReadTimeout，用来控制从 Socket 上读取数据的最长等待时间。对于定时任务或异步任务，可以设置为长一点，而对于用户响应的请求或微服务短平快的同步接口调用，需要设置短些，以防止下游服务超时，导致积压大量线程，系统崩溃
 */
@RestController
@RequestMapping("clientreadtimeout")
@Slf4j
public class HttpReadTimeOutController {
    private String getResponse(String url, int connectTimeout, int readTimeout) throws IOException {
        return Request.Get("http://localhost:8080/clientreadtimeout" + url)
                .connectTimeout(connectTimeout)
                .socketTimeout(readTimeout)
                .execute()
                .returnContent()
                .asString();
    }

    @GetMapping("client")
    public String client() throws IOException {
        log.info("client1 called");
        //服务端5s超时，客户端读取超时2秒
        return getResponse("/server?timeout=5000", 1000, 2000);
    }

    @GetMapping("server")
    public void server(@RequestParam("timeout") int timeout) throws InterruptedException {
        log.info("server called");
        TimeUnit.MILLISECONDS.sleep(timeout);
        log.info("Done");
    }
}
