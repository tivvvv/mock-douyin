package com.tiv.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 邮件DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {

    /**
     * 接收方
     */
    private String to;

    /**
     * 发送方
     */
    private String from;

    /**
     * 主题
     */
    private String subject;

    /**
     * 内容
     */
    private String text;
}
