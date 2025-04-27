package com.tiv.service.impl;

import com.github.pagehelper.PageHelper;
import com.tiv.common.enums.YesOrNoEnum;
import com.tiv.common.result.PagedResult;
import com.tiv.mapper.FansMapper;
import com.tiv.mapper.FansMapperCustom;
import com.tiv.model.pojo.Fans;
import com.tiv.model.vo.VloggerVO;
import com.tiv.service.FanService;
import com.tiv.service.base.BaseInfoProperties;
import com.tiv.service.utils.RedisUtil;
import com.tiv.service.utils.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FanServiceImpl extends BaseInfoProperties implements FanService {

    private static final Logger log = LoggerFactory.getLogger(FanServiceImpl.class);
    @Autowired
    private FansMapper fansMapper;

    @Autowired
    private Sid sid;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FansMapperCustom fansMapperCustom;

    @Override
    public void doFollow(String userId, String vloggerId) {
        Fans fans = Fans
                .builder()
                .id(sid.nextShort())
                .fanId(userId)
                .vloggerId(vloggerId).build();
        // 判断对方是否关注我
        Fans vlogger = queryFanRelationship(vloggerId, userId);
        if (vlogger != null) {
            fans.setIsFriend(YesOrNoEnum.YES.type);
            vlogger.setIsFriend(YesOrNoEnum.YES.type);
            fansMapper.updateByPrimaryKeySelective(vlogger);
        } else {
            fans.setIsFriend(YesOrNoEnum.NO.type);
        }
        fansMapper.insert(fans);
    }

    @Transactional
    @Override
    public void doCancel(String userId, String vloggerId) {
        Fans fan = queryFanRelationship(userId, vloggerId);
        if (fan == null) {
            return;
        }
        if (fan.getIsFriend() == YesOrNoEnum.YES.type) {
            Fans pendingVlogger = queryFanRelationship(vloggerId, userId);
            if (pendingVlogger == null) {
                log.error("fanId:{} vloggerId:{}关系错误,vlogger不存在", userId, vloggerId);
                return;
            }
            pendingVlogger.setIsFriend(YesOrNoEnum.NO.type);
            fansMapper.updateByPrimaryKeySelective(pendingVlogger);
        }

        fansMapper.delete(fan);
    }

    @Override
    public boolean queryFollowStatus(String userId, String vloggerId) {
        return queryFanRelationship(userId, vloggerId) != null;
    }

    @Override
    public PagedResult<VloggerVO> queryFollowList(String userId, Integer page, Integer pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<VloggerVO> follows = fansMapperCustom.getFollows(map);

        return buildPagedResult(follows, page);
    }

    private Fans queryFanRelationship(String fanId, String vloggerId) {
        Example example = new Example(Fans.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("vloggerId", vloggerId);
        criteria.andEqualTo("fanId", fanId);
        List<Fans> fans = fansMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(fans)) {
            return null;
        }
        return fans.get(0);
    }
}
