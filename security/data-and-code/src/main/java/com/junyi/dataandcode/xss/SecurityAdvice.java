package com.junyi.dataandcode.xss;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.util.HtmlUtils;

import java.beans.PropertyEditorSupport;

/**
 * \@InitBinder 是 Spring Web 层面的处理逻辑，如果有代码不通过 @RequestParam 来获取数据，而是直接从 HTTP 请求获取数据的话，比如
 * user.setName(request.getParameter("username"))，这种方式就不会奏效。
 *
 * 更合理的解决方式是，定义一个 servlet Filter，通过 HttpServletRequestWrapper 实现 servlet 层面的统一参数替换：
 */
@ControllerAdvice
public class SecurityAdvice {

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }

            @Override
            public void setAsText(String text) {
                // 赋值时进行HTML转义
                // Escape all characters
                setValue(text == null ? null : HtmlUtils.htmlEscape(text));
            }
        });
    }
}
