package com.junyi.productionready.info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

/**
 * 通过actuator查看信息：http://localhost:56789/admin/info
 * 如果开启了JMX，那么可以通过JConsole连接后，在MBean中的org.springframework.boot.Endpoint中可以查看信息
 */
@SpringBootApplication
@PropertySource("actuator-info.properties")
public class CommonMistakesApplication {

    public static void main(String[] args) {

        SpringApplication.run(CommonMistakesApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}

