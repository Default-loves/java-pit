package com.junyi.redistemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * RedisTemplate 和 StringRedisTemplate 保存的数据无法通用，它们需要读取自己存的数据
 */

@RestController
@RequestMapping("redistemplate")
@Slf4j
public class RedisTemplateController {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private RedisTemplate<String, User> userRedisTemplate;
    @Autowired
    private RedisTemplate<String, Long> countRedisTemplate;


    @PostConstruct
    public void init() throws JsonProcessingException {
        redisTemplate.opsForValue().set("redisTemplate", new User("xujunyi", 18));
        stringRedisTemplate.opsForValue().set("stringRedisTemplate", objectMapper.writeValueAsString(new User("xujunyi", 18)));
    }

    @GetMapping("wrong")
    public void wrong() {
        // 得到的都是 null
        // RedisTemplate 和 StringRedisTemplate 保存的数据无法通用，它们需要读取自己存的数据
        log.info("redisTemplate get {}", redisTemplate.opsForValue().get("stringRedisTemplate"));
        log.info("stringRedisTemplate get {}", stringRedisTemplate.opsForValue().get("redisTemplate"));
    }

    @GetMapping("right")
    public void right() throws JsonProcessingException {
        //使用RedisTemplate获取Value，无需反序列化就可以拿到实际对象，虽然方便，但是Redis中保存的Key和Value不易读
        User userFromRedisTemplate = (User) redisTemplate.opsForValue().get("redisTemplate");
        log.info("redisTemplate get {}", userFromRedisTemplate);

        //使用StringRedisTemplate，虽然Key正常，但是Value存取需要手动序列化成字符串
        User userFromStringRedisTemplate = objectMapper.readValue(stringRedisTemplate.opsForValue().get("stringRedisTemplate"), User.class);
        log.info("stringRedisTemplate get {}", userFromStringRedisTemplate);
    }

    @GetMapping("right2")
    public void right2() {
        User user = new User("xujunyi", 18);
        userRedisTemplate.opsForValue().set(user.getName(), user);
        User userFromRedis = userRedisTemplate.opsForValue().get(user.getName());
        log.info("userRedisTemplate get {} {}", userFromRedis, userFromRedis.getClass());
        log.info("stringRedisTemplate get {}", stringRedisTemplate.opsForValue().get(user.getName()));
    }

    @GetMapping("wrong2")
    public void wrong2() {
        String key = "testCounter";
        countRedisTemplate.opsForValue().set(key, 1L);
        log.info("{} {}", countRedisTemplate.opsForValue().get(key), countRedisTemplate.opsForValue().get(key) instanceof Long);
        Long l1 = getLongFromRedis(key);
        countRedisTemplate.opsForValue().set(key, Integer.MAX_VALUE + 1L);
        log.info("{} {}", countRedisTemplate.opsForValue().get(key), countRedisTemplate.opsForValue().get(key) instanceof Long);
        Long l2 = getLongFromRedis(key);
        log.info("{} {}", l1, l2);
    }


    private Long getLongFromRedis(String key) {
        Object o = countRedisTemplate.opsForValue().get(key);
        if (o instanceof Integer) {
            return ((Integer) o).longValue();
        }
        if (o instanceof Long) {
            return (Long) o;
        }
        return null;
    }
}
