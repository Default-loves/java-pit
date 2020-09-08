package com.junyi.equal.lombokequals;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("lombokequals")
public class LombokEquealsController {

    @GetMapping("test1")
    public void test1() {
        Person person1 = new Person("zhuye", "001");
        Person person2 = new Person("Joseph", "001");
        log.info("person1.equals(person2) ? {}", person1.equals(person2));
    }

    @GetMapping("test2")
    public void test2() {
        Employee employee1 = new Employee("zhuye", "001", "bkjk.com");
        Employee employee2 = new Employee("Joseph", "002", "bkjk.com");
        log.info("employee1.equals(employee2) ? {}", employee1.equals(employee2));
    }


    @Data
    class Person {
        // equal 和 hashCode 方法只考虑 identity 字段
        @EqualsAndHashCode.Exclude
        private String name;
        private String identity;

        public Person(String name, String identity) {
            this.name = name;
            this.identity = identity;
        }
    }

    @Data
    @EqualsAndHashCode(callSuper = true)    // equal 和 hashCode 方法需要考虑父类的字段
    class Employee extends Person {

        private String company;

        public Employee(String name, String identity, String company) {
            super(name, identity);
            this.company = company;
        }
    }
}
