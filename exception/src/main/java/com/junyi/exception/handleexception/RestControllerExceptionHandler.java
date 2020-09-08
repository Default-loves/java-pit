package com.junyi.exception.handleexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * 不建议在框架层面进行异常的自动、统一处理，尤其不要随意捕获异常。但，框架可以做兜底工作。
 * 如果异常上升到最上层逻辑还是无法处理的话，可以以统一的方式进行异常转换，比如通过 @RestControllerAdvice + @ExceptionHandler，来捕获这些“未处理”异常
 */
@RestControllerAdvice
@Slf4j
public class RestControllerExceptionHandler {

    private static int GENERIC_SERVER_ERROR_CODE = 2000;
    private static String GENERIC_SERVER_ERROR_MESSAGE = "服务器忙，请稍后再试";

    @ExceptionHandler
    public APIResponse handle(HttpServletRequest req, HandlerMethod method, Exception ex) {
        if (ex instanceof BusinessException) {
            BusinessException exception = (BusinessException) ex;
            log.warn(String.format("访问 %s -> %s 出现业务异常！", req.getRequestURI(), method.toString()), ex);
            return new APIResponse(false, null, exception.getCode(), exception.getMessage());
        } else {
            log.error(String.format("访问 %s -> %s 出现系统异常！", req.getRequestURI(), method.toString()), ex);
            return new APIResponse(false, null, GENERIC_SERVER_ERROR_CODE, GENERIC_SERVER_ERROR_MESSAGE);
        }
    }
}
