package com.junyi.numerical.rounding;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * 字符格式化的问题
 */
@SpringBootApplication
public class CommonMistakesApplication {

    public static void main(String[] args) {
        wrong1();
        wrong2();
        right();
    }

    private static void wrong1() {
        System.out.println("wrong1");
        double num1 = 3.35;     // 3.350000000000000088817841970012523233890533447265625
        float num2 = 3.35f;     // 3.349999904632568359375
        System.out.println(String.format("%.1f", num1));    // 3.4
        System.out.println(String.format("%.1f", num2));    // 3.3
    }

    private static void wrong2() {
        System.out.println("wrong2");
        double num1 = 3.35;
        float num2 = 3.35f;
        DecimalFormat format = new DecimalFormat("#.##");
        format.setRoundingMode(RoundingMode.DOWN);
        System.out.println(format.format(num1));    // 3.35
        format.setRoundingMode(RoundingMode.DOWN);
        System.out.println(format.format(num2));    // 3.34
    }


    private static void right() {
        System.out.println("right");
        BigDecimal num1 = new BigDecimal("3.35");
        BigDecimal num2 = num1.setScale(1, BigDecimal.ROUND_DOWN);  // 3.3
        System.out.println(num2);
        BigDecimal num3 = num1.setScale(1, BigDecimal.ROUND_HALF_UP);   // 3.4
        System.out.println(num3);
    }
}

