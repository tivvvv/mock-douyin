package com.tiv.api.controller;

import com.tiv.common.constant.Constants;
import com.tiv.common.result.GraceJSONResult;
import com.tiv.model.dto.EmailDTO;
import com.tiv.service.EmailService;
import com.tiv.service.utils.IPUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录接口
 */
@Api
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController extends BaseController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/code")
    public GraceJSONResult getVerificationCode(@RequestParam String email, HttpServletRequest request) {

        if (StringUtils.isBlank(email)) {
            return GraceJSONResult.ok();
        }

        // 同一ip地址,一分钟内只能发送一次验证码
        String ip = IPUtil.getRequestIp(request);
        redis.setnx60s(Constants.IP_PREFIX + ip, ip);
        // 随机生成6位验证码
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        EmailDTO emailDTO = EmailDTO
                .builder()
                .to(email)
                .subject("mock-douyin验证码")
                .text(code)
                .build();
        emailService.sendEmail(emailDTO);
        // 验证码有效时间为10min
        redis.set(Constants.VERIFY_CODE_PREFIX + email, code, 10 * 60);
        return GraceJSONResult.ok();
    }
}
