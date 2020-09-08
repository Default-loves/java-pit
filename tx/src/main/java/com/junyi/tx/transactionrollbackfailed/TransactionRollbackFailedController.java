package com.junyi.tx.transactionrollbackfailed;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * wrong1 的服务方法中的try虽然抛出了异常，但是被异常被catch吃了，没有传播到方法外面触发事务回滚
 * wrong2 的服务方法调用了其他方法抛出IOException，默认情况下，出现 RuntimeException（非受检异常）或 Error 的时候，Spring 才会回滚事务
 * right1 方法在catch中手动调用回滚
 * right2 方法在Transaction注解中手动添加了对所有的Exception都进行回滚，rollbackFor = Exception.class
 */
@RestController
@RequestMapping("transactionrollbackfailed")
@Slf4j
public class TransactionRollbackFailedController {

    @Autowired
    private UserService userService;

    @GetMapping("wrong1")
    public int wrong1(@RequestParam("name") String name) {
        userService.createUserWrong1(name);
        return userService.getUserCount(name);
    }

    @GetMapping("wrong2")
    public int wrong2(@RequestParam("name") String name) {
        try {
            userService.createUserWrong2(name);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
        return userService.getUserCount(name);
    }

    @GetMapping("right1")
    public int right1(@RequestParam("name") String name) {
        try {
            userService.createUserRight1(name);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
        return userService.getUserCount(name);
    }

    @GetMapping("right2")
    public int right2(@RequestParam("name") String name) {
        try {
            userService.createUserRight2(name);
        } catch (Exception e) {
            log.error("create user failed", e);
        }
        return userService.getUserCount(name);
    }
}
