package com.tiv.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 用户
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 手机号
     */
    private String mail;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 唯一标识,可以付费修改
     */
    private String uid;

    /**
     * 头像
     */
    private String face;

    /**
     * 性别 0:女 1:男 2:保密
     */
    private Integer sex;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区县
     */
    private String district;

    /**
     * 简介
     */
    private String description;

    /**
     * 背景图
     */
    @Column(name = "bg_img")
    private String bgImg;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "modify_time")
    private Date modifyTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 修改人
     */
    private String modifier;
}