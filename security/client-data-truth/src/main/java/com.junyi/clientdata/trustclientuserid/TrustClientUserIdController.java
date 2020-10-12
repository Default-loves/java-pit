package com.junyi.clientdata.trustclientuserid;

import com.junyi.clientdata.trustclientuserid.LoginRequired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@Slf4j
@RequestMapping("trustclientuserid")
@RestController
public class TrustClientUserIdController {

    @GetMapping("wrong")
    public String wrong(@RequestParam("userId") Long userId) {
        return "当前用户Id：" + userId;
    }

    /** userId comes from client should not be use, right to get from session */
    @GetMapping("right")
    public String right(@LoginRequired Long userId) {
        return "当前用户Id：" + userId;
    }

    @GetMapping("login")
    public long login(@RequestParam("username") String username, @RequestParam("password") String password, HttpSession session) {
        if (username.equals("admin") && password.equals("admin")) {
            log.info("User [{}] login", username);
            session.setAttribute("currentUser", 1L);
            return 1L;
        }
        return 0L;
    }
}
