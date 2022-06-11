package com.junyi;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Set;

/**
 * @time: 2020/9/29 16:23
 * @version: 1.0
 * @author: junyi Xu
 * @description:
 */
@Slf4j
public class EmailAliases {

    private Set<String> aliases;

    private EmailAliases(HashMap<String, String> h) {
        aliases = h.keySet();
    }

    public void printKeys() {
        String s = Double.toString(1.1d);

        log.info("Mail keys:%n");
        for (String k : aliases)
            log.info("{}", k);
    }
}
