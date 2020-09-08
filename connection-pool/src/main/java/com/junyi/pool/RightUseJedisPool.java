package com.junyi.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;

/**
 * @time: 2020/8/19 9:00
 * @version: 1.0
 * @author: junyi Xu
 * @description: 使用Jedis要通过Jedis Pool获取Jedis来使用，Jedis Pool是线程安全的
 */
public class RightUseJedisPool {
    public static final Logger log = LoggerFactory.getLogger(RightUseJedisPool.class);

    private static JedisPool jedisPool = new JedisPool("127.0.0.1", 6379);



    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            jedisPool.close();
        }));
    }

    public void right() {
        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                for (int i = 0; i < 1000; i++) {
                    String result = jedis.get("a");
                    if (!result.equals("1")) {
                        log.warn("Expect a to be 1 but found {}", result);
                        return;
                    }
                }
            }
        }).start();
        new Thread(() -> {
            try (Jedis jedis = jedisPool.getResource()) {
                for (int i = 0; i < 1000; i++) {
                    String result = jedis.get("b");
                    if (!result.equals("2")) {
                        log.warn("Expect b to be 2 but found {}", result);
                        return;
                    }
                }
            }
        }).start();
    }
}
