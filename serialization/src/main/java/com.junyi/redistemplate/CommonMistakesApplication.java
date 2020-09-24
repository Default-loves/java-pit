package com.junyi.redistemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * use redisTemplate and stringRedisTemplate
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
        //把类型信息作为属性写入Value, The same effect with: redisTemplate.setValueSerializer(RedisSerializer.json());
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        /** custom Key and Value serializer: Key is RedisSerializer.string, Value is JSON
         *  and then we can use RedisTemplate<String, User>
         */
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);  //希望 Value 也是使用 JSON 序列化
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        /** Default Value class type is LinkedHashMap, this can set the right class type into Value. The same effect with ObjectMapper
          * So if uses this, don't need to new ObjectMapper above
          */
        redisTemplate.setValueSerializer(RedisSerializer.json());

        // 查看这个方法可以发现 redisTemplate 默认使用了 JdkSerializationRedisSerializer，对Key和Value进行序列化和反序列化，因此容易产生 Redis 中保存了乱码的错觉
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

