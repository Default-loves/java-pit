package com.junyi.weakhashmapoom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

/**
 * Right to use WeakHashMap, better to use ConcurrentReferenceHashMap
 * WeakHashMap 的特点是 Key 在哈希表内部是弱引用的，当没有强引用指向这个 Key 之后，Entry 会被 GC，即使我们无限往 WeakHashMap 加入数据，只要 Key 不再使用，也就不会 OOM。
 * ConcurrentReferenceHashMap's Key and Value will be wrapped by SoftReference or WeakReference
 */
@RestController
@RequestMapping("weakhashmapoom")
@Slf4j
public class WeakHashMapOOMController {
    private Map<User, UserProfile> weakHashMap = new WeakHashMap<>();
    private Map<User, WeakReference<UserProfile>> weakHashMapUpgrade = new WeakHashMap<>();
    private Map<User, UserProfile> concurrentReferenceHashMap = new ConcurrentReferenceHashMap<>();

    @GetMapping("wrong")
    public void wrong() {
        String userName = "zhuye";
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> log.info("cache size:{}", weakHashMap.size()), 1, 1, TimeUnit.SECONDS);
        /** UserProfile strong references User, so data will not been GC  */
        LongStream.rangeClosed(1, 2000000).forEach(i -> {
            User user = new User(userName + i);
            weakHashMap.put(user, new UserProfile(user, "location" + i));
        });
    }

    @GetMapping("right")
    public void right() {
        String userName = "zhuye";
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> log.info("cache size:{}", weakHashMapUpgrade.size()), 1, 1, TimeUnit.SECONDS);
        /** use WeakReference value */
        LongStream.rangeClosed(1, 2000000).forEach(i -> {
            User user = new User(userName + i);
            weakHashMapUpgrade.put(user, new WeakReference(new UserProfile(user, "location" + i)));
        });
    }

    @GetMapping("right2")
    public void right2() {
        String userName = "zhuye";
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> log.info("cache size:{}", weakHashMap.size()), 1, 1, TimeUnit.SECONDS);
        /** UserProfile references to new User */
        LongStream.rangeClosed(1, 2000000).forEach(i -> {
            User user = new User(userName + i);
            weakHashMap.put(user, new UserProfile(new User(user.getName()), "location" + i));
        });
    }

    @GetMapping("right3")
    public void right3() {
        String userName = "zhuye";
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(
                () -> log.info("cache size:{}", concurrentReferenceHashMap.size()), 1, 1, TimeUnit.SECONDS);
        LongStream.rangeClosed(1, 20000000).forEach(i -> {
            User user = new User(userName + i);
            concurrentReferenceHashMap.put(user, new UserProfile(user, "location" + i));
        });
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class User {
        private String name;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class UserProfile {
        private User user;
        private String location;
    }
}
