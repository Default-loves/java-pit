package com.junyi.numerical.dangerousdouble;


import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * 对于精度有一定要求的时候不要使用 double 等浮点数，其精度是有问题，而应该使用 BigDecimal
 * 而且需要注意的是 BigDecimal中的参数需要是 String，而不是 Number
 */
@Slf4j
public class CommonMistakesApplication {

    public static void main(String[] args) {

        testScale();
        System.out.println("wrong1");
        wrong1();
        System.out.println("wrong2");
        wrong2();
        System.out.println("right");
        right();
    }

    private static void wrong1() {

        System.out.println(0.1 + 0.2);  //  0.30000000000000004
        System.out.println(1.0 - 0.8);  // 0.19999999999999996
        System.out.println(4.015 * 100);    //    401.49999999999994
        System.out.println(123.3 / 100);    //    1.2329999999999999

        double amount1 = 2.15;
        double amount2 = 1.10;

        if (amount1 - amount2 == 1.05)  // 不会相等
            System.out.println("OK");
    }

    private static void testScale() {
        BigDecimal bigDecimal1 = new BigDecimal("100");
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(100d));
        BigDecimal bigDecimal3 = new BigDecimal(String.valueOf(100));
        BigDecimal bigDecimal4 = BigDecimal.valueOf(100d);
        BigDecimal bigDecimal5 = new BigDecimal(Double.toString(100));

        print(bigDecimal1); //scale 0 precision 3 result 401.500
        print(bigDecimal2); //scale 1 precision 4 result 401.5000
        print(bigDecimal3); //scale 0 precision 3 result 401.500
        print(bigDecimal4); //scale 1 precision 4 result 401.5000
        print(bigDecimal5); //scale 1 precision 4 result 401.5000
    }

    private static void print(BigDecimal bigDecimal) {
        log.info("scale {} precision {} result {}", bigDecimal.scale(), bigDecimal.precision(), bigDecimal.multiply(new BigDecimal("4.015")));
    }

    private static void wrong2() {
        System.out.println(new BigDecimal(0.1).add(new BigDecimal(0.2)));
        System.out.println(new BigDecimal(1.0).subtract(new BigDecimal(0.8)));
        System.out.println(new BigDecimal(4.015).multiply(new BigDecimal(100)));
        System.out.println(new BigDecimal(123.3).divide(new BigDecimal(100)));
    }

    private static void right() {
        System.out.println(new BigDecimal("0.1").add(new BigDecimal("0.2")));
        System.out.println(new BigDecimal("1.0").subtract(new BigDecimal("0.8")));
        System.out.println(new BigDecimal("4.015").multiply(new BigDecimal("100")));
        System.out.println(new BigDecimal("123.3").divide(new BigDecimal("100")));

    }
}

