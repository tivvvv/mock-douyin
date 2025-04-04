package com.tiv.service;

import com.tiv.model.bo.UpdateUserBO;
import com.tiv.model.pojo.Users;

public interface UserService {

    /**
     * 根据邮箱获取用户
     *
     * @param email
     * @return
     */
    Users getUserByEmail(String email);

    /**
     * 创建用户
     *
     * @param email
     * @return
     */
    Users createUser(String email);

    /**
     * 根据用户id获取用户
     *
     * @param userId
     * @return
     */
    Users getUser(String userId);

    /**
     * 修改用户信息
     *
     * @param updateUserBO
     * @return
     */
    Users updateUser(UpdateUserBO updateUserBO);

    /**
     * 修改用户信息
     *
     * @param updateUserBO
     * @param type
     * @return
     */
    Users updateUser(UpdateUserBO updateUserBO, Integer type);
}
