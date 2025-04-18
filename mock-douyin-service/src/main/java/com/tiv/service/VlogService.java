package com.tiv.service;

import com.tiv.common.result.PagedResult;
import com.tiv.model.bo.VlogBO;
import com.tiv.model.vo.IndexVlogVO;

public interface VlogService {

    /**
     * 创建短视频
     *
     * @param vlogBO
     */
    void createVlog(VlogBO vlogBO);

    /**
     * 分页查询短视频列表
     *
     * @param search
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult<IndexVlogVO> getIndexVlogList(String search, Integer page, Integer pageSize);

    /**
     * 查询短视频详情
     *
     * @param userId
     * @param vlogId
     * @return
     */
    IndexVlogVO getVlogDetailById(String userId, String vlogId);
}
