package com.junyi.deserializationconstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 默认情况下，在反序列化的时候，Jackson 框架只会调用无参构造方法创建对象。
 * wrong 方法 success字段总是 false
 */
@RestController
@RequestMapping("deserializationconstructor")
@Slf4j
public class DeserializationConstructorController {
    @Autowired
    ObjectMapper objectMapper;


    @GetMapping("wrong")
    public void wrong() throws JsonProcessingException {
        log.info("result :{}", objectMapper.readValue("{\"code\":1234}", APIResultWrong.class));
        log.info("result :{}", objectMapper.readValue("{\"code\":2000}", APIResultWrong.class));
    }

    @GetMapping("right")
    public void right() throws JsonProcessingException {
        log.info("result :{}", objectMapper.readValue("{\"code\":1234}", APIResultRight.class));
        log.info("result :{}", objectMapper.readValue("{\"code\":2000}", APIResultRight.class));
    }
}
