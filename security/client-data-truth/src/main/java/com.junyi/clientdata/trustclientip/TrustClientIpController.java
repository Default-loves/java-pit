package com.junyi.clientdata.trustclientip;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;

/**
 * 更好的做法是，让用户进行登录或三方授权登录（比如微信），拿到用户标识来做唯一性判断。
 */
@Slf4j
@RequestMapping("trustclientip")
@RestController
public class TrustClientIpController {

    HashSet<String> activityLimit = new HashSet<>();

    @GetMapping("test")
    public String test(HttpServletRequest request) {
        String ip = getClientIp(request);
        if (activityLimit.contains(ip)) {
            return "您已经领取过奖品";
        } else {
            activityLimit.add(ip);
            return "奖品领取成功";
        }
    }

    /** Firstly get from X-Forwarded-For, then get from getRemoteAddr() */
    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff == null) {
            return request.getRemoteAddr();
        } else {
            return xff.contains(",") ? xff.split(",")[0] : xff;
        }
    }
}
