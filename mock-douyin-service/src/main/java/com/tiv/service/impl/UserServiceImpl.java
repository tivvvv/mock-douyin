package com.tiv.service.impl;

import com.tiv.common.constant.Constants;
import com.tiv.common.enums.ResponseStatusEnum;
import com.tiv.common.enums.SexEnum;
import com.tiv.common.enums.UserInfoModifyTypeEnum;
import com.tiv.common.exception.GraceException;
import com.tiv.mapper.UsersMapper;
import com.tiv.model.bo.UpdateUserBO;
import com.tiv.model.pojo.Users;
import com.tiv.service.UserService;
import com.tiv.service.utils.DateUtil;
import com.tiv.service.utils.DesensitizationUtil;
import com.tiv.service.utils.RedisUtil;
import com.tiv.service.utils.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private RedisUtil redis;

    @Override
    public Users getUserByEmail(String email) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("email", email);
        return usersMapper.selectOneByExample(userExample);
    }

    @Override
    public Users getUser(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Override
    public Users updateUser(UpdateUserBO updateUserBO) {
        Users pendingUser = new Users();
        BeanUtils.copyProperties(updateUserBO, pendingUser);

        int result = usersMapper.updateByPrimaryKeySelective(pendingUser);
        if (result != 1) {
            throw new GraceException(ResponseStatusEnum.USER_UPDATE_ERROR);
        }

        return getUser(updateUserBO.getId());
    }

    @Override
    public Users updateUser(UpdateUserBO updateUserBO, Integer type) {

        if (type == UserInfoModifyTypeEnum.NICKNAME.getType()) {
            Example example = new Example(Users.class);
            example.createCriteria().andEqualTo(Constants.NICKNAME, updateUserBO.getNickname());
            Users user = usersMapper.selectOneByExample(example);
            // 昵称已存在
            if (user != null) {
                throw new GraceException(ResponseStatusEnum.USER_INFO_UPDATED_NICKNAME_EXIST_ERROR);
            }
        }

        if (type == UserInfoModifyTypeEnum.UID.getType()) {
            Example example = new Example(Users.class);
            example.createCriteria().andEqualTo(Constants.UID, updateUserBO.getUid());
            Users user = usersMapper.selectOneByExample(example);
            // UID已存在
            if (user != null) {
                throw new GraceException(ResponseStatusEnum.USER_INFO_UPDATED_UID_EXIST_ERROR);
            }
            // 没有可用的UID修改次数
            if (redis.get(Constants.UPDATE_UID_PREFIX + updateUserBO.getId()) != null) {
                throw new GraceException(ResponseStatusEnum.USER_INFO_UPDATED_UID_NO_CHANCE_ERROR);
            }
            // 一年内不能再次修改,除非付费
            redis.set(Constants.UPDATE_UID_PREFIX + updateUserBO.getId(), updateUserBO.getId(), 60 * 60 * 24 * 365);
        }

        return updateUser(updateUserBO);
    }

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
