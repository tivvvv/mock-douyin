package com.tiv.model.bo;

import lombok.*;

import java.util.Date;

/**
 * 更新用户BO
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserBO {

    private String id;

    private String nickname;
    private String uid;
    private String face;
    private Integer sex;
    private Date birthday;
    private String description;
    private String bgImg;
}