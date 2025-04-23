package com.tiv.service;

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
}
