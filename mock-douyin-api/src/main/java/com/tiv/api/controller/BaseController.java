package com.tiv.api.controller;

import com.tiv.service.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础接口
 */
public class BaseController {

    @Autowired
    public RedisUtil redis;

}
