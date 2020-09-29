package com.junyi;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @time: 2020/9/16 12:01
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */



@Slf4j
public class RestoreAliases {

    private static Map<String, String> defaultAliases = new HashMap<String, String>();
    static {
        defaultAliases.put("Duke", "duke@i-love-java");
        defaultAliases.put("Fang", "fang@evil-jealous-twin");
    }

    public static void main(String... args) {
        try {
            Constructor ctor = EmailAliases.class.getDeclaredConstructor(HashMap.class);
            ctor.setAccessible(true);
            EmailAliases email = (EmailAliases)ctor.newInstance(defaultAliases);
            email.printKeys();

            // production code should handle these exceptions more gracefully
        } catch (InstantiationException x) {
            x.printStackTrace();
        } catch (IllegalAccessException x) {
            x.printStackTrace();
        } catch (InvocationTargetException x) {
            x.printStackTrace();
        } catch (NoSuchMethodException x) {
            x.printStackTrace();
        }
    }
}