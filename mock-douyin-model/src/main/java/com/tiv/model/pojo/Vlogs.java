package com.tiv.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 短视频
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vlogs {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 短视频作者
     */
    @Column(name = "vlogger_id")
    private String vloggerId;

    /**
     * 短视频播放地址
     */
    private String url;

    /**
     * 短视频封面
     */
    private String cover;

    /**
     * 短视频标题
     */
    private String title;

    /**
     * 短视频宽度
     */
    private Integer width;

    /**
     * 短视频高度
     */
    private Integer height;

    /**
     * 点赞总数
     */
    @Column(name = "like_counts")
    private Integer likeCounts;

    /**
     * 评论总数
     */
    @Column(name = "comment_counts")
    private Integer commentCounts;

    /**
     * 是否私密
     */
    @Column(name = "is_private")
    private Integer isPrivate;

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