package com.junyi.nullvalue.avoidnullpointerexception;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 常见的空指针场景：
 * 1. 参数值是 Integer 等包装类型，使用时因为自动拆箱出现了空指针异常；
 * 2. 字符串比较出现空指针异常；
 * 3. 诸如 ConcurrentHashMap 这样的容器不支持 Key 和 Value 为 null，强行 put null 的 Key 或 Value 会出现空指针异常；
 * 4. A 对象包含了 B，在通过 A 对象的字段获得 B 之后，没有对字段判空就级联调用 B 的方法出现空指针异常；
 * 5. 方法或远程服务返回的 List 不是空而是 null，没有进行判空就直接调用 List 的方法出现空指针异常。
 *
 * 使用判空方式或 Optional 方式来避免出现空指针异常，不一定是解决问题的最好方式，空指针没出现可能隐藏了更深的 Bug，因为不会报错，且没有异常信息堆栈
 */
@RestController
@RequestMapping("avoidnullpointerexception")
@Slf4j
public class AvoidNullPointerExceptionController {

    @GetMapping("wrong")
    public int wrong(@RequestParam(value = "test", defaultValue = "1111") String test) {
        return wrongMethod(test.charAt(0) == '1' ? null : new FooService(),
                test.charAt(1) == '1' ? null : 1,
                test.charAt(2) == '1' ? null : "OK",
                test.charAt(3) == '1' ? null : "OK").size();
    }

    @GetMapping("right")
    public int right(@RequestParam(value = "test", defaultValue = "1111") String test) {
        return Optional.ofNullable(rightMethod(test.charAt(0) == '1' ? null : new FooService(),
                test.charAt(1) == '1' ? null : 1,
                test.charAt(2) == '1' ? null : "OK",
                test.charAt(3) == '1' ? null : "OK"))
                .orElse(Collections.emptyList()).size();

    }


    private List<String> wrongMethod(FooService fooService, Integer i, String s, String t) {
        log.info("result {} {} {} {}", i + 1, s.equals("OK"), s.equals(t),
                new ConcurrentHashMap<String, String>().put(null, null));
        if (fooService.getBarService().bar().equals("OK"))
            log.info("OK");
        return null;
    }

    private List<String> rightMethod(FooService fooService, Integer i, String s, String t) {
        log.info("result {} {} {} {}", Optional.ofNullable(i).orElse(0) + 1, "OK".equals(s), Objects.equals(s, t), new HashMap<String, String>().put(null, null));
        Optional.ofNullable(fooService)
                .map(FooService::getBarService)
                .filter(barService -> "OK".equals(barService.bar()))
                .ifPresent(result -> log.info("OK"));
        return new ArrayList<>();
    }

    class FooService {
        @Getter
        private BarService barService;

    }

    class BarService {
        String bar() {
            return "OK";
        }
    }
}
