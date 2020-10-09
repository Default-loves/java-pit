package com.junyi.productionready.health;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class User {
    private long userId;
    private String userName;

    public User(){}

    public User(long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
