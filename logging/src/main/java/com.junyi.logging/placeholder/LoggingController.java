package com.junyi.logging.placeholder;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 使用{}占位符语法不能通过延迟参数值获取，来解决日志数据获取的性能问题
 * 可以通过 lambda 表达式进行延迟参数内容获取
 * SLF4J 的 API 还不支持 lambda，因此需要使用 Log4j2 日志 API，把 Lombok 的 @Slf4j 注解替换为 @Log4j2 注解
 */
//@Slf4j
@Log4j2
@RequestMapping("logging")
@RestController
public class LoggingController {

    @GetMapping
    public void index() {
        StopWatch stopWatch = new StopWatch();

        stopWatch.start("debug1");
        log.debug("debug1:" + slowString("debug1"));
        stopWatch.stop();

        stopWatch.start("debug2");
        log.debug("debug2:{}", slowString("debug2"));
        stopWatch.stop();

        stopWatch.start("debug3");
        if (log.isDebugEnabled())
            log.debug("debug3:{}", slowString("debug3"));
        stopWatch.stop();

        stopWatch.start("debug4");
        log.debug("debug4:{}", () -> slowString("debug4")); //debug4 并不会调用 slowString 方法
        stopWatch.stop();
        log.info(stopWatch.prettyPrint());

    }

    private String slowString(String s) {
        System.out.println("slowString called via " + s);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
        }
        return "OK";
    }
}
