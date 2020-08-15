package com.junyi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @time: 2020/8/13 22:09
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

/**
 * /wrong演示的是错误的适用方式，/rightr是正确的方式
 * 在/wrong中ThreadLocal由于没有删除，信息还保留在线程中，而线程是会重用的，线程可能服务于另一个用户，会导致信息错误
 */
@RestController
public class MyController {

    private static final ThreadLocal<Integer> currentUser = ThreadLocal.withInitial(() -> null);

    @GetMapping("wrong")
    public Map wrong(@RequestParam Integer id) {
        String before = Thread.currentThread().getName() + ": " + currentUser.get();
        currentUser.set(id);
        String after = Thread.currentThread().getName() + ": " + currentUser.get();
        Map<String, String> map = new HashMap<>();
        map.put("before", before);
        map.put("after", after);
        return map;
    }

    @GetMapping("right")
    public Map right(@RequestParam Integer id) {
        String before = Thread.currentThread().getName() + ": " + currentUser.get();
        try {
            currentUser.set(id);
            String after = Thread.currentThread().getName() + ": " + currentUser.get();
            Map<String, String> map = new HashMap<>();
            map.put("before", before);
            map.put("after", after);
            return map;
        } finally {
            currentUser.remove();
        }
    }
}
