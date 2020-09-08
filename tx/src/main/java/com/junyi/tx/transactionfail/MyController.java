package com.junyi.tx.transactionfail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @time: 2020/8/25 14:02
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 * wrong1方法，service中使用了private的事务方法
 * wrong2的方法，由于service中使用了this调用方法，而this指向的是没有代理后的类，因此事务不会生效
 * right1的方法，直接调用标注Transaction注解的方法
 */
@Slf4j
@RestController
public class MyController {

    @Autowired
    private UserService userService;


    @GetMapping("wrong1")
    public int wrong1(@RequestParam("name") String name) {
        return userService.createUserWrong1(name);
    }

    @GetMapping("wrong2")
    public int wrong2(@RequestParam("name") String name) {
        return userService.createUserWrong2(name);
    }


    @GetMapping("right1")
    public int right1(@RequestParam("name") String name) {
        try {
            userService.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userService.getUserCount(name);
    }
}
