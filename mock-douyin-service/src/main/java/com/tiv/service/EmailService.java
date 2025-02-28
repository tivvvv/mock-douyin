package com.tiv.service;

import com.tiv.model.dto.EmailDTO;

/**
 * 邮件服务接口
 */
public interface EmailService {

    /**
     * 发送邮件
     *
     * @param emailDTO
     * @return
     */
    void sendEmail(EmailDTO emailDTO);
}
