package com.tiv.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 粉丝关联
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Fans {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 作家用户id
     */
    @Column(name = "vlogger_id")
    private String vloggerId;

    /**
     * 粉丝用户id
     */
    @Column(name = "fan_id")
    private String fanId;

    /**
     * 0:仅关注 1:互粉
     */
    @Column(name = "is_friend")
    private Integer isFriend;

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