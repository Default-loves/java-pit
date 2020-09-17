package com.junyi.deserializationconstructor;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 通过在构造方法上添加 JsonCreator 注解让对象反序列化的时候执行自定义的构造方法
 */

@Data
public class APIResultRight {
    private boolean success;
    private int code;

    public APIResultRight() {
    }

    @JsonCreator
    public APIResultRight(@JsonProperty("code") int code) {
        this.code = code;
        success = code == 2000;
    }
}
