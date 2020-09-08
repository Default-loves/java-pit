package com.junyi.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Properties;

/**
 * @time: 2020/8/19 21:05
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class Util {

    public static void loadProperties(Class claz, String fileName){
        Properties p = new Properties();
        try {
            p.load(claz.getResourceAsStream(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        p.forEach((k, v) -> {
            log.info("Load: {}= {}", k, v);
            System.setProperty(k.toString(), v.toString());
        });
    }
}
