package com.tiv.common.enums;

import com.tiv.common.exception.GraceException;
import lombok.Getter;

import java.util.Arrays;

/**
 * 修改用户信息类型枚举
 */
@Getter
public enum UserInfoModifyTypeEnum {

    NICKNAME(1, "昵称"),
    UID(2, "唯一标识"),
    SEX(3, "性别"),
    BIRTHDAY(4, "生日"),
    DESC(5, "简介");

    private final int type;
    private final String value;

    UserInfoModifyTypeEnum(int type, String value) {
        this.type = type;
        this.value = value;
    }

    public static void checkUserInfoType(int type) {
        if (Arrays.stream(UserInfoModifyTypeEnum.values())
                .noneMatch(enumType -> enumType.getType() == type)) {
            throw new GraceException(ResponseStatusEnum.USER_INFO_UPDATED_ERROR);
        }
    }
}
