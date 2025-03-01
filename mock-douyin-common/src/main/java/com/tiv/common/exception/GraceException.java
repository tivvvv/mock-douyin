package com.tiv.common.exception;

import com.tiv.common.enums.ResponseStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 */
@Setter
@Getter
public class GraceException extends RuntimeException {

    private ResponseStatusEnum responseStatusEnum;

    public GraceException(ResponseStatusEnum responseStatusEnum) {
        super(String.format("异常状态码为:%s,具体异常信息为:%s", responseStatusEnum.getCode(), responseStatusEnum.getMsg()));
        this.responseStatusEnum = responseStatusEnum;
    }

}
