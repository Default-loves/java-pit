package com.junyi;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @time: 2020/9/29 16:25
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class aaa {

    private static final Map<String, Object> map = new HashMap<>();
    static {
        map.put("id", "123");
        map.put("name", "apple");
    }

    private <T> void test1(Class<T> tClass) {
        T target;
        try {
            Object object = tClass.newInstance();
            target = (T) object;
            Field field = target.getClass().getDeclaredField("id");
            field.setAccessible(true);
            field.set(target, map.get("id"));
        } catch (InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        log.info(JSONUtil.toJson())
    }
    public static void main(String[] args) throws Exception {

            // create user object
            Employee emp = new Employee();

            // print value of uniqueNo
            System.out.println(
                    "Value of uniqueNo before "
                            + "applying set is "
                            + emp.uniqueNo);

            // Get the field object
            Field field
                    = Employee.class
                    .getField("uniqueNo");

            // Apply set Method
            field.set(emp, (short)1213);

            // print value of uniqueNo
            System.out.println(
                    "Value of uniqueNo after "
                            + "applying set is "
                            + emp.uniqueNo);

            // print value of salary
            System.out.println(
                    "Value of salary before "
                            + "applying set is "
                            + emp.salary);

            // Get the field object
            field = Employee.class.getField("salary");

            // Apply set Method
            field.set(emp, 324344.2323);

            // print value of salary
            System.out.println(
                    "Value of salary after "
                            + "applying set is "
                            + emp.salary);
        }
    }

    // sample class
    class Employee {

        // static values
        public static short uniqueNo = 239;
        public static double salary = 121324.13333;
    }



