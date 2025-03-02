package com.tiv.api.handler;

import com.tiv.common.exception.GraceException;
import com.tiv.common.result.GraceJSONResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 自定义异常拦截器
 */
@ControllerAdvice
public class GraceExceptionHandler {
    @ResponseBody
    @ExceptionHandler(GraceException.class)
    public GraceJSONResult returnGraceException(GraceException e) {
        e.printStackTrace();
        return GraceJSONResult.exception(e.getResponseStatusEnum());
    }
}
