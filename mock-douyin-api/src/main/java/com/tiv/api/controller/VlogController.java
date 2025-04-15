package com.tiv.api.controller;

import com.tiv.common.result.GraceJSONResult;
import com.tiv.model.bo.VlogBO;
import com.tiv.service.VlogService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
