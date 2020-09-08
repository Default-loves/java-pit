package com.junyi.tx.transactionfail;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @time: 2020/8/25 13:58
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */

@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;

    public UserEntity() { }

    public UserEntity(String name) {
        this.name = name;
    }
}