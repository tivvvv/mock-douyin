package com.tiv.common.enums;

/**
 * 文件类型枚举
 */
public enum FileTypeEnum {

    BG_IMG(1, "用户背景图"),
    FACE(2, "用户头像");

    public final int type;
    public final String value;

    FileTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }
}
