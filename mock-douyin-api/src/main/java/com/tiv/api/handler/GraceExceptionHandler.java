package com.tiv.api.handler;

import com.tiv.common.enums.ResponseStatusEnum;
import com.tiv.common.exception.GraceException;
import com.tiv.common.result.GraceJSONResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自定义异常拦截器
 */
@ControllerAdvice
public class GraceExceptionHandler {

    /**
     * 自定义异常拦截器
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(GraceException.class)
    public GraceJSONResult returnGraceException(GraceException e) {
        e.printStackTrace();
        return GraceJSONResult.exception(e.getResponseStatusEnum());
    }

    @ResponseBody
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public GraceJSONResult returnMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        e.printStackTrace();
        return GraceJSONResult.exception(ResponseStatusEnum.FILE_MAX_SIZE_5MB_ERROR);
    }

    /**
     * hibernate校验异常拦截器.
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GraceJSONResult returnMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        return GraceJSONResult.errorMap(getErrors(bindingResult));
    }

    public Map<String, String> getErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();
        List<FieldError> fieldErrorList = result.getFieldErrors();
        for (FieldError fieldError : fieldErrorList) {
            // 错误对应的字段名
            String field = fieldError.getField();
            // 错误信息
            String msg = fieldError.getDefaultMessage();
            errorMap.put(field, msg);
        }
        return errorMap;
    }
}
