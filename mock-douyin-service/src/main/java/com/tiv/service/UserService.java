package com.tiv.service;

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
}
