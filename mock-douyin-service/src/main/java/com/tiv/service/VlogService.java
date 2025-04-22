package com.tiv.service;

import com.tiv.common.result.PagedResult;
import com.tiv.model.bo.VlogBO;
import com.tiv.model.pojo.Vlogs;
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

    /**
     * 设置短视频私密/公开
     *
     * @param userId
     * @param vlogId
     * @param isPrivate
     */
    void changePrivate(String userId, String vlogId, Integer isPrivate);

    /**
     * 分页查询我的短视频列表
     *
     * @param userId
     * @param isPrivate
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult<Vlogs> queryMyVlogList(String userId, Integer isPrivate, Integer page, Integer pageSize);
}
