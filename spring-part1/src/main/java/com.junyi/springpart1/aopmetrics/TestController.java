package com.junyi.springpart1.aopmetrics;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @see TestAspectWithOrder10
 * @see TestAspectWithOrder20
 * Run Order:
 * 10Around.before
 * 10Before
 * 20Around.before
 * 20Before
 * 20Around.after
 * 20After
 * 10Around.after
 * 10After
 *
 * So:
 * 1. @Around run before @Before and @After;
 * 2. Before Method run: low @Order run first
 * 3. After Method run: high @Order run first
 */
@Slf4j
@RequestMapping("test")
@RestController

public class TestController {
    @GetMapping
    public void test() {

    }
}
