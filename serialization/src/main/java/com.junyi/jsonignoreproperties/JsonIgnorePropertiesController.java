package com.junyi.jsonignoreproperties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jsonignoreproperties")
@Slf4j
public class JsonIgnorePropertiesController {

    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("test")
    public void test() throws JsonProcessingException {
        // 默认情况下是输出：“BLUE“
        log.info("color:{}", objectMapper.writeValueAsString(Color.BLUE));
    }

    /**
     * UserWrong中只有一个name字段，如果传入不存在的字段，Spring Web的ObjectMapper会忽略未知字段，如果自定义了 ObjectMapper Bean，
     * 导致覆盖了 Spring Web的Objectmapper，则对未知字段会报错
     */
    @PostMapping("wrong")
    public UserWrong wrong(@RequestBody UserWrong user) {
        return user;
    }

    @PostMapping("right")
    public Object right(@RequestBody UserRight user) {
        return user;
    }

    enum Color {
        RED, BLUE
    }
}
