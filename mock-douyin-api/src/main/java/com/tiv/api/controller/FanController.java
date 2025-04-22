package com.tiv.api.controller;

import com.tiv.common.enums.ResponseStatusEnum;
import com.tiv.common.result.GraceJSONResult;
import com.tiv.model.pojo.Users;
import com.tiv.service.FanService;
import com.tiv.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api
@Slf4j
@RestController
@RequestMapping("/fan")
public class FanController {

    @Autowired
    private UserService userService;

    @Autowired
    private FanService fanService;

    @PostMapping("/follow")
    public GraceJSONResult follow(@RequestParam String userId, @RequestParam String vloggerId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(vloggerId) || userId.equalsIgnoreCase(vloggerId)) {
            return GraceJSONResult.exception(ResponseStatusEnum.SYSTEM_PARAM_ERROR);
        }

        Users user = userService.getUser(userId);
        Users vlogger = userService.getUser(vloggerId);

        if (user == null || vlogger == null) {
            return GraceJSONResult.exception(ResponseStatusEnum.USER_NOT_EXIST_ERROR);
        }

        fanService.doFollow(userId, vloggerId);
        return GraceJSONResult.ok();
    }
}
