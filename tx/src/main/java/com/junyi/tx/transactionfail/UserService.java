package com.junyi.tx.transactionfail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @time: 2020/8/25 14:00
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 *
 */

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //一个公共方法供Controller调用，内部调用事务性的私有方法
    public int createUserWrong1(String name) {
        try {
            this.createUserPrivate(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    public int createUserWrong2(String name) {
        try {
            this.createUserPublic(new UserEntity(name));
        } catch (Exception ex) {
            log.error("create user failed because {}", ex.getMessage());
        }
        return userRepository.findByName(name).size();
    }

    //标记了@Transactional的public方法
    @Transactional
    public void createUserPublic(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test"))
            throw new RuntimeException("invalid username!");
    }


    //标记了@Transactional的private方法
    @Transactional
    private void createUserPrivate(UserEntity entity) {
        userRepository.save(entity);
        if (entity.getName().contains("test"))
            throw new RuntimeException("invalid username!");
    }

    //根据用户名查询用户数
    public int getUserCount(String name) {
        return userRepository.findByName(name).size();
    }
}