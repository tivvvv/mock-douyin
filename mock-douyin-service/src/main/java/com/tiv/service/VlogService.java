package com.tiv.service;

import com.tiv.model.bo.VlogBO;
import com.tiv.model.vo.IndexVlogVO;

import java.util.List;

public interface VlogService {

    /**
     * 创建短视频
     *
     * @param vlogBO
     */
    void createVlog(VlogBO vlogBO);

    /**
     * 查询短视频列表
     *
     * @param search
     * @return
     */
    List<IndexVlogVO> getIndexVlogList(String search);
}
