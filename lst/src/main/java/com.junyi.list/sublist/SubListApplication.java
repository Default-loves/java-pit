package com.junyi.list.sublist;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * list.subList 返回的 List 与 list 是强关联的， 两者会互相影响
 *
 * 1. 将 subList()返回的 List 作为参数构造一个新的 ArrayList
 * 2. 使用 stream 操作替代 subList 方法
 */
public class SubListApplication {

    private static List<List<Integer>> data = new ArrayList<>();

    private static void wrong() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = list.subList(1, 4);
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        try {
            subList.forEach(System.out::println);   // ConcurrentModificationException
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {

//        oom();
        wrong();
        //right1();
//        right2();
        //oomfix();
    }

    /**
     * rawList.subList 返回的 List 与 rawList 是强关联的，导致 rawList 不能被垃圾回收，导致有大量的rawList存在于内存
     */
    private static void oom() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(rawList.subList(0, 1));
        }
    }

    private static void oomfix() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(new ArrayList<>(rawList.subList(0, 1)));
        }
    }

    private static void right1() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = new ArrayList<>(list.subList(1, 4));
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        subList.forEach(System.out::println);
    }

    private static void right2() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = list.stream().skip(1).limit(3).collect(Collectors.toList());
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        subList.forEach(System.out::println);
    }
}

