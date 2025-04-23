package com.tiv.api.controller;

import com.tiv.common.constant.Constants;
import com.tiv.common.enums.ResponseStatusEnum;
import com.tiv.common.result.GraceJSONResult;
import com.tiv.model.pojo.Users;
import com.tiv.service.FanService;
import com.tiv.service.UserService;
import com.tiv.service.utils.RedisUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@Slf4j
@RestController
@RequestMapping("/fan")
public class FanController {

    @Autowired
    private UserService userService;

    @Autowired
    private FanService fanService;
    @Autowired
    private RedisUtil redisUtil;

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

        // 我的关注数+1
        redisUtil.increment(Constants.MY_FOLLOWS_COUNTS_PREFIX + userId, 1);
        // 博主粉丝数+1
        redisUtil.increment(Constants.MY_FANS_COUNTS_PREFIX + vloggerId, 1);
        // 关注关系
        redisUtil.set(Constants.FAN_REL_VLOGGER_PREFIX + userId + ":" + vloggerId, "1");

        return GraceJSONResult.ok();
    }

    @PostMapping("/unfollow")
    public GraceJSONResult unfollow(@RequestParam String userId, @RequestParam String vloggerId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(vloggerId) || userId.equalsIgnoreCase(vloggerId)) {
            return GraceJSONResult.exception(ResponseStatusEnum.SYSTEM_PARAM_ERROR);
        }
        fanService.doCancel(userId, vloggerId);

        redisUtil.decrement(Constants.MY_FOLLOWS_COUNTS_PREFIX + userId, 1);
        redisUtil.decrement(Constants.MY_FANS_COUNTS_PREFIX + vloggerId, 1);
        redisUtil.del(Constants.FAN_REL_VLOGGER_PREFIX + userId + ":" + vloggerId);

        return GraceJSONResult.ok();
    }

    @GetMapping("/queryFollowStatus")
    public GraceJSONResult queryFollowStatus(@RequestParam String userId, @RequestParam String vloggerId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(vloggerId) || userId.equalsIgnoreCase(vloggerId)) {
            return GraceJSONResult.exception(ResponseStatusEnum.SYSTEM_PARAM_ERROR);
        }
        boolean isFollow = fanService.queryFollowStatus(userId, vloggerId);
        return GraceJSONResult.ok(isFollow);
    }

}
