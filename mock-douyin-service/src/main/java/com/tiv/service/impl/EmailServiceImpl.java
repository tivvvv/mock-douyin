package com.tiv.service.impl;

import com.tiv.model.dto.EmailDTO;
import com.tiv.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Date;

/**
 * 邮件服务实现类
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendEmail(EmailDTO emailDTO) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(javaMailSender.createMimeMessage(), true);
            messageHelper.setFrom(from);
            messageHelper.setTo(emailDTO.getTo());
            messageHelper.setSubject(emailDTO.getSubject());
            messageHelper.setText(emailDTO.getText());
            messageHelper.setSentDate(new Date());
            javaMailSender.send(messageHelper.getMimeMessage());
        } catch (MessagingException e) {
            throw new RuntimeException("邮件发送失败: ", e);
        }
    }
}
