package com.tiv.common.enums;

import lombok.Getter;

/**
 * 性别枚举
 */
@Getter
public enum SexEnum {

    woman(0, "女"),
    man(1, "男"),
    secret(2, "保密");

    private final int type;
    private final String value;

    SexEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
