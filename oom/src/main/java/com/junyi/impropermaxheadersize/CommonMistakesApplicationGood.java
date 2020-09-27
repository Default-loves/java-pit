package com.junyi.impropermaxheadersize;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OOM Reason: Tomcat parameter configuration is unreasonable
 */

@SpringBootApplication
@Slf4j
public class CommonMistakesApplicationGood {
    public static void main(String[] args) {
//        Utils.loadPropertySource(CommonMistakesApplicationGood.class, "good.properties");
        SpringApplication.run(CommonMistakesApplicationGood.class, args);
    }

}
