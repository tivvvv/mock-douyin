package com.tiv.api.controller;

import com.tiv.common.result.GraceJSONResult;
import com.tiv.model.bo.VlogBO;
import com.tiv.service.VlogService;
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

    @PostMapping("/publish")
    public GraceJSONResult publish(@RequestBody VlogBO vlogBO) {
        vlogService.createVlog(vlogBO);
        return GraceJSONResult.ok();
    }

    @GetMapping("/indexList")
    public GraceJSONResult indexList(@RequestParam(defaultValue = "") String search,
                                     @RequestParam(defaultValue = "1") Integer page,
                                     @RequestParam(defaultValue = "10") Integer pageSize) {
        return GraceJSONResult.ok(vlogService.getIndexVlogList(search, page, pageSize));
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

    @GetMapping()
    public GraceJSONResult myVlogList(@RequestParam String userId,
                                      @RequestParam Integer isPrivate,
                                      @RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue = "10") Integer pageSize) {
        return GraceJSONResult.ok(vlogService.queryMyVlogList(userId, isPrivate, page, pageSize));
    }

}
