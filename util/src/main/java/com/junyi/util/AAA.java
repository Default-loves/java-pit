package com.junyi.util;

import javafx.util.Pair;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.*;

/**
 * @time: 2020/8/24 15:09
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class AAA {
    static class Person {
        private Integer id;
        private String name;

        public Person() {
        }

        public Person(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }

    public List<List<Integer>> getSkyline(int[][] buildings) {
        PriorityQueue<Pair<Integer, Integer>> pairs = new PriorityQueue<>();
        for (int[] item: buildings) {
            pairs.add(new Pair<>(item[0], -item[2]));
            pairs.add(new Pair<>(item[0], item[2]));
        }

        List<List<Integer>> res = new ArrayList<>();
        return res;
    }
    public static void main(String[] args) {
//        AAA demo = new AAA();
//        int[] array = new int[]{7,2,5,10,8};
//        int rotateSteps = demo.splitArray(array, 2);
//        System.out.println(rotateSteps);
        ArrayList<Person> list = new ArrayList<>();
        Person p1 = new Person(1, "junyi");
        Person p2 = new Person(2, "xiaoming");
        list.add(p1);
        list.add(p2);
        Integer i = new Integer(2);
        Person res = list.get(0);
        System.out.println(res);
    }
}
