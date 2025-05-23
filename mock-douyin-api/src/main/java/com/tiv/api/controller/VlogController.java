package com.tiv.api.controller;

import com.tiv.common.constant.Constants;
import com.tiv.common.result.GraceJSONResult;
import com.tiv.model.bo.VlogBO;
import com.tiv.service.VlogService;
import com.tiv.service.utils.RedisUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@Slf4j
@RestController
@RequestMapping("/vlog")
public class VlogController {

    @Autowired
    private VlogService vlogService;
    @Autowired
    private RedisUtil redisUtil;

    @PostMapping("/publish")
    public GraceJSONResult publish(@RequestBody VlogBO vlogBO) {
        vlogService.createVlog(vlogBO);
        return GraceJSONResult.ok();
    }

    @GetMapping("/indexList")
    public GraceJSONResult indexList(@RequestParam String userId,
                                     @RequestParam(defaultValue = "") String search,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return GraceJSONResult.ok(vlogService.getIndexVlogList(userId, search, page, pageSize));
    }

    @GetMapping("/detail")
    public GraceJSONResult detail(@RequestParam(defaultValue = "") String userId,
                                  @RequestParam String vlogId) {
        return GraceJSONResult.ok(vlogService.getVlogDetailById(userId, vlogId));
    }

    @PostMapping("/changePrivate")
    public GraceJSONResult publish(@RequestParam String userId,
                                   @RequestParam String vlogId,
                                   @RequestParam Integer isPrivate) {
        vlogService.changePrivate(userId, vlogId, isPrivate);
        return GraceJSONResult.ok();
    }

    @GetMapping("/myVlogList")
    public GraceJSONResult myVlogList(@RequestParam String userId,
                                      @RequestParam Integer isPrivate,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return GraceJSONResult.ok(vlogService.queryMyVlogList(userId, isPrivate, page, pageSize));
    }

    @PostMapping("/like")
    public GraceJSONResult like(@RequestParam String userId,
                                @RequestParam String vlogId,
                                @RequestParam String vloggerId) {
        vlogService.likeVlog(userId, vlogId);
        redisUtil.increment(Constants.MY_LIKES_COUNTS_PREFIX + vloggerId, 1);
        redisUtil.increment(Constants.VLOG_LIKE_COUNTS_PREFIX + vlogId, 1);
        redisUtil.set(Constants.USER_LIKE_VLOG_PREFIX + userId + ":" + vlogId, "1");
        return GraceJSONResult.ok();
    }

    @PostMapping("/unlike")
    public GraceJSONResult unlike(@RequestParam String userId,
                                  @RequestParam String vlogId,
                                  @RequestParam String vloggerId) {
        vlogService.unlikeVlog(userId, vlogId);
        redisUtil.decrement(Constants.MY_LIKES_COUNTS_PREFIX + vloggerId, 1);
        redisUtil.decrement(Constants.VLOG_LIKE_COUNTS_PREFIX + vlogId, 1);
        redisUtil.del(Constants.USER_LIKE_VLOG_PREFIX + userId + ":" + vlogId);
        return GraceJSONResult.ok();
    }

    @GetMapping("/vlogLikeCounts")
    public GraceJSONResult vlogLikeCounts(@RequestParam String vlogId) {
        return GraceJSONResult.ok(vlogService.getVlogLikeCounts(vlogId));
    }

    @GetMapping("/likedVlogs")
    public GraceJSONResult likedVlogList(@RequestParam String userId,
                                         @RequestParam(defaultValue = "1") Integer page,
                                         @RequestParam(defaultValue = "10") Integer pageSize) {
        return GraceJSONResult.ok(vlogService.getLikedVlogList(userId, page, pageSize));
    }

    @GetMapping("/followedVlogs")
    public GraceJSONResult followedVlogList(@RequestParam String userId,
                                            @RequestParam(defaultValue = "1") Integer page,
                                            @RequestParam(defaultValue = "10") Integer pageSize) {
        return GraceJSONResult.ok(vlogService.getFollowedVlogList(userId, page, pageSize));
    }

    @GetMapping("/friendsVlogs")
    public GraceJSONResult friendsVlogList(@RequestParam String userId,
                                           @RequestParam(defaultValue = "1") Integer page,
                                           @RequestParam(defaultValue = "10") Integer pageSize) {
        return GraceJSONResult.ok(vlogService.getFriendsVlogList(userId, page, pageSize));
    }

}
