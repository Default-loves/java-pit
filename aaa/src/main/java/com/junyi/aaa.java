package com.junyi;

import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.LongAdder;

/**
 * @time: 2020/9/29 16:25
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class aaa {

    public static void main(String[] args) {
        String s = "小";
        String pattern = ".*" + s + "*";
        boolean matches = "大车场".matches(pattern);
        System.out.println(matches);
        HashMap<String, String> map = new HashMap<>();

    }


}





