package com.tiv.model.vo;

import lombok.*;

import java.util.Date;

/**
 * 用户VO
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsersVO {

    private String id;
    private String email;
    private String nickname;
    private String uid;
    private String face;
    private Integer sex;
    private Date birthday;
    private String country;
    private String province;
    private String city;
    private String district;
    private String description;
    private String bgImg;
    private Date createTime;
    private Date modifyTime;
    private String creator;
    private String modifier;

    private String userToken;

}