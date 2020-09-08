package com.junyi.numerical.equals;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * BigDecimal 的 equal、compareTo 方法
 * equals 比较的是 BigDecimal 的 value 和 scale
 * compareTo 比较的是 BigDecimai 的 value
 */
public class CommonMistakesApplication {

    public static void main(String[] args) {
        wrong();
        right();
        set();
    }

    private static void wrong() {
        System.out.println(new BigDecimal("1.0").equals(new BigDecimal("1")));  // false
    }

    private static void right() {
        System.out.println(new BigDecimal("1.0").compareTo(new BigDecimal("1")) == 0);  // true
    }

    private static void set() {
        Set<BigDecimal> hashSet1 = new HashSet<>();
        hashSet1.add(new BigDecimal("1.0"));
        System.out.println(hashSet1.contains(new BigDecimal("1")));     //返回false

        // 1、将小数点后面的0都去掉
        Set<BigDecimal> hashSet2 = new HashSet<>();
        hashSet2.add(new BigDecimal("1.0").stripTrailingZeros());
        System.out.println(hashSet2.contains(new BigDecimal("1.000").stripTrailingZeros()));    //返回true

        // 2、使用TreeSet
        Set<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(new BigDecimal("1.0"));
        System.out.println(treeSet.contains(new BigDecimal("1")));      //返回true
    }

}

