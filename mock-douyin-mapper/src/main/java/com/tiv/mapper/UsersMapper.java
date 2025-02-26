package com.tiv.mapper;

import com.tiv.mapper.my.MyMapper;
import com.tiv.model.pojo.Users;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersMapper extends MyMapper<Users> {
}