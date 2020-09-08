package com.junyi.ribbonretry;

import com.junyi.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// HTTP的Get请求在失败后，会尝试进行重试，而Post方法不会自动重试
// 在properties文件中配置不重试

@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {

        Util.loadProperties(CommonMistakesApplication.class,"/default-ribbon.properties");
        SpringApplication.run(CommonMistakesApplication.class, args);
    }
}

