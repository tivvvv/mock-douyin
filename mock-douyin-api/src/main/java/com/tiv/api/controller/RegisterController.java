package com.tiv.api.controller;

import com.tiv.common.constant.Constants;
import com.tiv.common.enums.ResponseStatusEnum;
import com.tiv.common.result.GraceJSONResult;
import com.tiv.model.bo.LoginBO;
import com.tiv.model.dto.EmailDTO;
import com.tiv.model.pojo.Users;
import com.tiv.model.vo.UsersVO;
import com.tiv.service.EmailService;
import com.tiv.service.UserService;
import com.tiv.service.utils.IPUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.UUID;

/**
 * 注册接口
 */
@Api
@Slf4j
@RestController
@RequestMapping("/register")
public class RegisterController extends BaseController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserService userService;

    /**
     * 获取验证码
     *
     * @param email
     * @param request
     * @return
     */
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
        log.info("邮箱:{},验证码:{}", email, code);
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

    /**
     * 登录
     *
     * @param loginBO
     * @param request
     * @return
     */
    @PostMapping("/login")
    public GraceJSONResult login(@Valid @RequestBody LoginBO loginBO, HttpServletRequest request) {

        String email = loginBO.getEmail();
        String code = loginBO.getCode();

        // 校验验证码是否匹配
        String redisCode = redis.get(Constants.VERIFY_CODE_PREFIX + email);
        if (StringUtils.isBlank(redisCode) || !redisCode.equalsIgnoreCase(code)) {
            return GraceJSONResult.errorCustom(ResponseStatusEnum.CODE_ERROR);
        }

        // 查询用户是否存在
        Users user = userService.getUserByEmail(email);
        if (user == null) {
            user = userService.createUser(email);
        }

        // 缓存用户会话信息
        String userToken = UUID.randomUUID().toString();
        redis.set(Constants.USER_TOKEN_PREFIX + user.getId(), userToken, 3600 * 24 * 7);

        // 清除验证码
        redis.del(Constants.VERIFY_CODE_PREFIX + email);

        // 返回用户信息
        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setUserToken(userToken);

        return GraceJSONResult.ok(userVO);
    }

    /**
     * 登出
     *
     * @param userId
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public GraceJSONResult logout(@RequestParam String userId, HttpServletRequest request) {
        redis.del(Constants.USER_TOKEN_PREFIX + userId);
        return GraceJSONResult.ok();
    }
}
