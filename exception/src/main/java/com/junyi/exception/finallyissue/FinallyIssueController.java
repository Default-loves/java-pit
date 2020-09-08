package com.junyi.exception.finallyissue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * finally中的异常问题
 *
 * wrong：finally抛出了异常，但是方法只能够抛出一个异常，因此finally抛出的异常会覆盖try的异常
 * right：finally中的异常直接进行捕获
 * right2：将try中的异常作为主异常，将finally的异常附加到主异常后面
 * useresourcewrong：finally方法会抛出异常，从而覆盖掉try中的异常
 * useresourceright：对于获取资源，最好使用try...with..resourcec
 *
 */
@RestController
@Slf4j
@RequestMapping("finallyissue")
public class FinallyIssueController {

    @GetMapping("useresourcewrong")
    public void useresourcewrong() throws Exception {
        TestResource testResource = new TestResource();
        try {
            testResource.read();
        } finally {
            testResource.close();
        }
    }

    @GetMapping("useresourceright")
    public void useresourceright() throws Exception {
        try (TestResource testResource = new TestResource()) {
            testResource.read();
        }
    }

    @GetMapping("wrong")
    public void wrong() {
        try {
            log.info("try");
            throw new RuntimeException("try");
        } finally {
            log.info("finally");
            throw new RuntimeException("finally");
        }
    }

    @GetMapping("right")
    public void right() {
        try {
            log.info("try");
            throw new RuntimeException("try");
        } finally {
            log.info("finally");
            try {
                throw new RuntimeException("finally");
            } catch (Exception ex) {
                log.error("finally", ex);
            }
        }
    }

    @GetMapping("right2")
    public void right2() throws Exception {
        Exception e = null;
        try {
            log.info("try");
            throw new RuntimeException("try");
        } catch (Exception ex) {
            e = ex;
        } finally {
            log.info("finally");
            try {
                throw new RuntimeException("finally");
            } catch (Exception ex) {
                if (e != null) {
                    e.addSuppressed(ex);
                } else {
                    e = ex;
                }
            }
        }
        throw e;
    }
}
