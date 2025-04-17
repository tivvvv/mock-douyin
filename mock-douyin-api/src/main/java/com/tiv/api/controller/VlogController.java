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
    public GraceJSONResult indexList(@RequestParam(defaultValue = "") String search) {
        return GraceJSONResult.ok(vlogService.getIndexVlogList(search));
    }
}
