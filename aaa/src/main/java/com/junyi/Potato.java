package com.junyi;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

/**
 * @time: 2020/9/16 12:01
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class Potato {
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
                System.out.println("Time over");
                System.exit(0);
            }
            Duration between = Duration.between(LocalTime.now(), after);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            System.out.println("The Remaining Time: "+ between.toMinutes() + " : " +
                    between.getSeconds() % 60);
        }
    }
}
