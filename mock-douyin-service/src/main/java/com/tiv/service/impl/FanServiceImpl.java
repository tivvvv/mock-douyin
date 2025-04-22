package com.tiv.service.impl;

import com.tiv.common.enums.YesOrNoEnum;
import com.tiv.mapper.FansMapper;
import com.tiv.model.pojo.Fans;
import com.tiv.service.FanService;
import com.tiv.service.utils.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class FanServiceImpl implements FanService {

    @Autowired
    private FansMapper fansMapper;

    @Autowired
    private Sid sid;

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
