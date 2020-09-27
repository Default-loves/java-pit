package com.junyi.springpart1.beansingletonandorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;

/**
 * add "SCOPE_PROTOTYPE" to make this class not singleton, and because controller is singleton, so the service autowired in controller is singleton,
 * add "proxyMode = ScopedProxyMode.TARGET_CLASS" mean controller get service from proxy, proxy will new a service difference from old
 */
@Service
@Slf4j
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SayHello extends SayService {

    @Override
    public void say() {
        super.say();
        log.info("hello");
    }
}
