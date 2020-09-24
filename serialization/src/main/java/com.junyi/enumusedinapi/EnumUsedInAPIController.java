package com.junyi.enumusedinapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RequestMapping("enumusedinapi")
@RestController
public class EnumUsedInAPIController {
    @Autowired
    private RestTemplate restTemplate;

    /** server response "CANCELED", which don't exist in client, the result will use default value */
    @GetMapping("getOrderStatusClient")
    public void getOrderStatusClient() {
        StatusEnumClient result = restTemplate.getForObject("http://localhost:1234/enumusedinapi/getOrderStatus", StatusEnumClient.class);
        log.info("result {}", result);
    }

    // 序列化走了 status 的值，而反序列化并没有根据 status 来，还是使用了枚举的 ordinal() 索引值。
    @GetMapping("queryOrdersByStatusListClient")
    public void queryOrdersByStatusListClient() {
        List<StatusEnumClient> request = Arrays.asList(StatusEnumClient.CREATED, StatusEnumClient.PAID);
        HttpEntity<List<StatusEnumClient>> entity = new HttpEntity<>(request, new HttpHeaders());
        List<StatusEnumClient> response = restTemplate.exchange("http://localhost:1234/enumusedinapi/queryOrdersByStatusList",
                HttpMethod.POST, entity, new ParameterizedTypeReference<List<StatusEnumClient>>() {
                }).getBody();
        log.info("result {}", response);
    }

    @GetMapping("getOrderStatus")
    public StatusEnumServer getOrderStatus() {
        return StatusEnumServer.CANCELED;
    }

    @PostMapping("queryOrdersByStatusList")
    public List<StatusEnumServer> queryOrdersByStatus(@RequestBody List<StatusEnumServer> enumServers) {
        enumServers.add(StatusEnumServer.CANCELED);
        return enumServers;
    }
}
