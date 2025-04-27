package com.tiv.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 粉丝VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FanVO {

    private String fanId;

    private String nickname;

    private String face;

    private Integer sex;

    private Boolean isFriend = false;

}