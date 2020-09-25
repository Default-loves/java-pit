package com.junyi;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
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
public class aaa {
    public static void main(String[] args) throws IOException, InterruptedException {
        LocalTime now = LocalTime.now();
//        LocalTime after = now.plusMinutes(33);
        LocalTime after = now.plusSeconds(5);
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            if (after.isBefore(LocalTime.now())) {
                log.info("Time over");
                System.exit(0);
            }
            Duration between = Duration.between(LocalTime.now(), after);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            log.info("The Remaining Time: {}: {}", between.toMinutes(),
                    between.getSeconds() % 60);
        }
    }
}
