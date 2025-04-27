package com.tiv.service;

import com.tiv.common.result.PagedResult;
import com.tiv.model.vo.FanVO;
import com.tiv.model.vo.VloggerVO;

public interface FanService {

    /**
     * 关注
     *
     * @param userId
     * @param vloggerId
     */
    void doFollow(String userId, String vloggerId);

    /**
     * 取关
     *
     * @param userId
     * @param vloggerId
     */
    void doCancel(String userId, String vloggerId);

    /**
     * 查询是否已关注
     *
     * @param userId
     * @param vloggerId
     * @return
     */
    boolean queryFollowStatus(String userId, String vloggerId);

    /**
     * 查询关注列表
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult<VloggerVO> queryFollowList(String userId, Integer page, Integer pageSize);

    /**
     * 查询粉丝列表
     *
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedResult<FanVO> queryFanList(String userId, Integer page, Integer pageSize);

}
