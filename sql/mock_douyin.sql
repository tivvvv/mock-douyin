DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`          varchar(24)  NOT NULL,
    `email`        varchar(32)  NOT NULL COMMENT '邮箱号',
    `password`    varchar(32)  NOT NULL COMMENT '密码',
    `nickname`    varchar(24)  NOT NULL COMMENT '昵称',
    `uid`         varchar(24)  NOT NULL COMMENT '唯一标识,可以付费修改',
    `face`        varchar(128) NOT NULL COMMENT '头像',
    `sex`         tinyint(2)   NOT NULL COMMENT '性别 0:女 1:男 2:保密',
    `birthday`    date         NOT NULL COMMENT '生日',
    `country`     varchar(32)  DEFAULT NULL COMMENT '国家',
    `province`    varchar(32)  DEFAULT NULL COMMENT '省份',
    `city`        varchar(32)  DEFAULT NULL COMMENT '城市',
    `district`    varchar(32)  DEFAULT NULL COMMENT '区县',
    `description` varchar(128) NOT NULL COMMENT '简介',
    `bg_img`      varchar(255) DEFAULT NULL COMMENT '背景图',
    `create_time` datetime     NOT NULL COMMENT '创建时间',
    `modify_time` datetime     NOT NULL COMMENT '更新时间',
    `creator`     varchar(24)  NOT NULL COMMENT '创建人',
    `modifier`    varchar(24)  NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_email` (`email`)
) COMMENT ='用户表';

DROP TABLE IF EXISTS `vlogs`;
CREATE TABLE `vlogs`
(
    `id`             varchar(24)  NOT NULL,
    `vlogger_id`     varchar(24)  NOT NULL COMMENT '短视频作者',
    `url`            varchar(255) NOT NULL COMMENT '短视频播放地址',
    `cover`          varchar(255) NOT NULL COMMENT '短视频封面',
    `title`          varchar(128) NOT NULL COMMENT '短视频标题',
    `width`          int(6)       NOT NULL COMMENT '短视频宽度',
    `height`         int(6)       NOT NULL COMMENT '短视频高度',
    `like_counts`    int(12)      NOT NULL COMMENT '点赞总数',
    `comment_counts` int(12)      NOT NULL COMMENT '评论总数',
    `is_private`     tinyint(2)   NOT NULL COMMENT '是否私密',
    `create_time`    datetime     NOT NULL COMMENT '创建时间',
    `modify_time`    datetime     NOT NULL COMMENT '更新时间',
    `creator`        varchar(24)  NOT NULL COMMENT '创建人',
    `modifier`       varchar(24)  NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) COMMENT ='短视频表';

DROP TABLE IF EXISTS `comments`;
CREATE TABLE `comments`
(
    `id`                varchar(24)  NOT NULL,
    `father_comment_id` varchar(24) DEFAULT NULL COMMENT '父级评论id',
    `vlog_id`           varchar(24)  NOT NULL COMMENT '评论所属短视频id',
    `user_id`           varchar(24)  NOT NULL COMMENT '评论用户id',
    `content`           varchar(128) NOT NULL COMMENT '评论内容',
    `like_counts`       int(20)      NOT NULL COMMENT '评论的点赞总数',
    `create_time`       datetime     NOT NULL COMMENT '创建时间',
    `modify_time`       datetime     NOT NULL COMMENT '更新时间',
    `creator`           varchar(24)  NOT NULL COMMENT '创建人',
    `modifier`          varchar(24)  NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`)
) COMMENT ='评论表';

DROP TABLE IF EXISTS `fans`;
CREATE TABLE `fans`
(
    `id`          varchar(24) NOT NULL,
    `vlogger_id`  varchar(24) NOT NULL COMMENT '作家用户id',
    `fan_id`      varchar(24) NOT NULL COMMENT '粉丝用户id',
    `is_friend`   tinyint(2)  NOT NULL COMMENT '0:仅关注 1:互粉',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `modify_time` datetime    NOT NULL COMMENT '更新时间',
    `creator`     varchar(24) NOT NULL COMMENT '创建人',
    `modifier`    varchar(24) NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_vlogger_fan_id` (`vlogger_id`, `fan_id`)
) COMMENT ='粉丝关联表';

DROP TABLE IF EXISTS `like_vlogs`;
CREATE TABLE `like_vlogs`
(
    `id`          varchar(24) NOT NULL,
    `user_id`     varchar(24) NOT NULL COMMENT '用户id',
    `vlog_id`     varchar(24) NOT NULL COMMENT '喜欢的短视频id',
    `create_time` datetime    NOT NULL COMMENT '创建时间',
    `modify_time` datetime    NOT NULL COMMENT '更新时间',
    `creator`     varchar(24) NOT NULL COMMENT '创建人',
    `modifier`    varchar(24) NOT NULL COMMENT '修改人',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uniq_user_vlog_id` (`user_id`, `vlog_id`)
) COMMENT ='短视频点赞关联表';
