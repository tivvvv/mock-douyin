package com.tiv.api.controller;

import com.tiv.common.constant.Constants;
import com.tiv.common.enums.UserInfoModifyTypeEnum;
import com.tiv.common.result.GraceJSONResult;
import com.tiv.model.bo.UpdateUserBO;
import com.tiv.model.pojo.Users;
import com.tiv.model.vo.UsersVO;
import com.tiv.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@Api
@Slf4j
@RequestMapping("/userInfo")
@RestController
public class UserInfoController extends BaseController {

    @Autowired
    private UserService userService;

    @GetMapping("/query")
    public GraceJSONResult queryUserInfo(@RequestParam String userId) {
        Users user = userService.getUser(userId);

        UsersVO userVO = new UsersVO();
        BeanUtils.copyProperties(user, userVO);

        // 关注数
        String followsCount = redis.get(Constants.FOLLOWS_COUNTS_PREFIX + userId);
        userVO.setFollowsCount(StringUtils.isBlank(followsCount) ? 0 : Integer.parseInt(followsCount));

        // 粉丝数
        String fansCount = redis.get(Constants.FANS_COUNTS_PREFIX + userId);
        userVO.setFansCount(StringUtils.isBlank(fansCount) ? 0 : Integer.parseInt(fansCount));

        // 获赞数
        String myLikesCount = redis.get(Constants.MY_LIKES_COUNTS_PREFIX + userId);
        userVO.setMyLikesCount(StringUtils.isBlank(myLikesCount) ? 0 : Integer.parseInt(myLikesCount));

        return GraceJSONResult.ok(userVO);
    }

    @PostMapping("/modify")
    public GraceJSONResult modifyUserInfo(@RequestBody UpdateUserBO updateUserBO, @RequestParam Integer type) {
        UserInfoModifyTypeEnum.checkUserInfoType(type);
        Users updatedUser = userService.updateUser(updateUserBO, type);
        return GraceJSONResult.ok(updatedUser);
    }
}
