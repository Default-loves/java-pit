package com.junyi.tx.transactionfail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @time: 2020/8/25 14:00
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findByName(String name);
}