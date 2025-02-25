package com.tiv.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * 评论
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comments {

    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 父级评论id
     */
    @Column(name = "father_comment_id")
    private String fatherCommentId;

    /**
     * 评论所属短视频id
     */
    @Column(name = "vlog_id")
    private String vlogId;

    /**
     * 评论用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论的点赞总数
     */
    @Column(name = "like_counts")
    private Integer likeCounts;

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