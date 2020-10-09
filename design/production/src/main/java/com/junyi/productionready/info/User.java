package com.junyi.productionready.info;

import lombok.Data;

@Data
public class User {
    private long userId;
    private String userName;

    public User() {
    }

    public User(long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
