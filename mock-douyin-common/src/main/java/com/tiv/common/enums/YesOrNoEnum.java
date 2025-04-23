package com.tiv.common.enums;

/**
 * 是否枚举
 */
public enum YesOrNoEnum {

    NO(0, "否"),
    YES(1, "是");

    public final int type;
    public final String value;

    YesOrNoEnum(Integer type, String value) {
        this.type = type;
        this.value = value;
    }
}
