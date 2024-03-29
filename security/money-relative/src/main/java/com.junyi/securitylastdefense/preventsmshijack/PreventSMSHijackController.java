package com.junyi.securitylastdefense.preventsmshijack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 防刷策略：
 * 1. 只有固定的请求头才能发送验证码
 * 2. 只有先来到过注册页面才能发送验证码
 * 3. 控制相同手机号一天只能发送10次验证码
 * 4. 控制相同手机号发送间隔1分钟
 * 5. 同一个IP超过阈值，短信验证码需要图形验证码前置
 */
@Slf4j
@RequestMapping("preventsmshijack")
@RestController
public class PreventSMSHijackController {
    @GetMapping("wrong")
    public void wrong() {
        sendSMSCaptcha("13600000000");
    }

    @GetMapping("right")
    public void right() {
        // 防刷手段
        sendSMSCaptcha("13600000000");
    }

    private void sendSMSCaptcha(String mobile) {
        //调用短信通道
    }
}
