package com.junyi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @time: 2020/9/16 12:01
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
public class aaa {

    private static final int target = 24;
    private static final double E = 1e-6;
    enum Opr {
        ADD(0, "ADD"),
        SUBTRACT(1, "SUBTRACT"),
        MULTIPLY(2, "MULTIPLY"),
        DIVIDE(3, "DIVIDE");

        private int index;
        private String name;

        Opr(int index, String name) {
            this.index = index;
            this.name = name;
        }

        public int getIndex() {
            return index;
        }

        public String getName() {
            return name;
        }
    }

    public boolean judgePoint24(int[] nums) {
        ArrayList<Double> res = new ArrayList<>();
        Arrays.stream(nums).forEach(k -> res.add((double) k));
        return f(res);
    }

    private boolean f(ArrayList<Double> list) {
        if (list.size() == 1) {
            return (list.get(0) - target) < E;
        }
        for (int i = 0; i < list.size(); i++) {
             for (int j = i+1; j < list.size(); j++) {
                 ArrayList<Double> cList = new ArrayList<>();
                 IntStream.range(0, list.size()).filter(index -> index!=i && index!=j)
                         .forEach(k -> cList.add(list.get(k)));
                 for (int k = 0; k < 4; k++) {
                      = array[k];

                 }

             }


        }

    }


    public static void main(String[] args) {
        aaa s = new aaa();
        String[] strings = new String[]{"abb","baa"};
//        s.minDeletionSize(strings);
        double a = 10e3;
        System.out.println(a);
    }
}
