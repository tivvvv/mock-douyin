package com.tiv.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 博主VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VloggerVO {

    private String vloggerId;

    private String nickname;

    private String face;

    private Integer sex;

    private Boolean isFollowed = true;

}