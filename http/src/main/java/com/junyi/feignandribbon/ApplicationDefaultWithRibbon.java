package com.junyi.feignandribbon;

import com.junyi.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

/**
 * @time: 2020/8/19 15:50
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@SpringBootApplication
public class ApplicationDefaultWithRibbon {

    public static void main(String[] args) throws IOException {
        Util.loadProperties(FeignAndRibbonController.class, "/default.properties");
        Util.loadProperties(FeignAndRibbonController.class, "/ribbon.properties");
        SpringApplication.run(ApplicationDefaultWithRibbon.class, args);
    }
}
