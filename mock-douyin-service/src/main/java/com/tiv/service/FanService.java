package com.tiv.service;

public interface FanService {

    /**
     * 关注
     *
     * @param userId
     * @param vloggerId
     */
    void doFollow(String userId, String vloggerId);
}
