package com.junyi.enumusedinapi;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
//        Utils.loadPropertySource(CommonMistakesApplication.class, "jackson.properties");

        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    /** support using enum default value when deserialize */
    @Bean
    public RestTemplate restTemplate(MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {
        return new RestTemplateBuilder()
                .additionalMessageConverters(mappingJackson2HttpMessageConverter)
                .build();
    }

    /** add costom EnumDeserializer */
    @Bean
    public Module enumModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Enum.class, new EnumDeserializer());
        return module;
    }
}

