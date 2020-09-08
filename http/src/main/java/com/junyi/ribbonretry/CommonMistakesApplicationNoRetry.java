package com.junyi.ribbonretry;

import com.junyi.util.Util;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CommonMistakesApplicationNoRetry {

    public static void main(String[] args) {

        Util.loadProperties(CommonMistakesApplicationNoRetry.class,"/noretry-ribbon.properties");
        SpringApplication.run(CommonMistakesApplicationNoRetry.class, args);
    }
}

