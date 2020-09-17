package com.junyi.jsonignoreproperties;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * 对于想序列化 Enum 数据的时候输出索引，不要直接定义 ObjectMapper，会覆盖 Spring Web 的 ObjectMapper
 * 可以考虑的做法是：
 * 1. User 类添加 JsonIgnoreProperties 注解
 * 2. 在Xml文件中配置
 * 3. 定义 Jackson2ObjectMapperBuilderCustomizer Bean
 */

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
//        Utils.loadPropertySource(CommonMistakesApplication.class, "jackson.properties");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    /**
     * 不要直接定义 ObjectMapper，会覆盖 Spring Web 的 ObjectMapper
     */
//    @Bean
//    public ObjectMapper objectMapper() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
//        return objectMapper;
//    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToEnable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
    }
}

