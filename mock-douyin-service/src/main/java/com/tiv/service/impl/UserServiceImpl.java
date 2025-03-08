package com.tiv.service.impl;

import com.tiv.common.constant.Constants;
import com.tiv.common.enums.SexEnum;
import com.tiv.mapper.UsersMapper;
import com.tiv.model.pojo.Users;
import com.tiv.service.UserService;
import com.tiv.service.utils.DateUtil;
import com.tiv.service.utils.DesensitizationUtil;
import com.tiv.service.utils.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Override
    public Users getUserByEmail(String email) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("email", email);
        return usersMapper.selectOneByExample(userExample);
    }

    @Transactional
    @Override
    public Users createUser(String email) {
        String userId = sid.nextShort();
        Users user = Users
                .builder()
                .id(userId)
                .email(email)
                .nickname("用户" + DesensitizationUtil.commonDisplay(email))
                .uid(DesensitizationUtil.commonDisplay(email))
                .face(Constants.USER_FACE_URI)
                .birthday(DateUtil.stringToDate("1900-01-01"))
                .sex(SexEnum.secret.getType())
                .description("这家伙很懒,什么都没有留下~")
                .creator(userId)
                .modifier(userId)
                .createTime(new Date())
                .modifyTime(new Date())
                .build();
        usersMapper.insert(user);
        return user;
    }
}
