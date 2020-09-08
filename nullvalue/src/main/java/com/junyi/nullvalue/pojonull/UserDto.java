package com.junyi.nullvalue.pojonull;

import lombok.Data;

import java.util.Optional;

@Data
public class UserDto {
    private Long id;
    private Optional<String> name;      //使用Optional，区分客户端不传数据还是故意传 null
    private Optional<Integer> age;
}
