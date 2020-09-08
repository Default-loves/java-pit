package com.junyi.feignandribbon;

import com.junyi.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @time: 2020/8/19 15:50
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SpringBootApplication
public class ApplicationDefault {

    public static void main(String[] args) throws IOException {
        Util.loadProperties(FeignAndRibbonController.class, "/default.properties");
        SpringApplication.run(ApplicationDefault.class, args);
    }
}
