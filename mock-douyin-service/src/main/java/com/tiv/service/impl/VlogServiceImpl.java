package com.tiv.service.impl;

import com.tiv.common.enums.YesOrNoEnum;
import com.tiv.mapper.VlogsMapper;
import com.tiv.model.bo.VlogBO;
import com.tiv.model.pojo.Vlogs;
import com.tiv.service.VlogService;
import com.tiv.service.utils.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class VlogServiceImpl implements VlogService {

    @Autowired
    private VlogsMapper vlogsMapper;

    @Autowired
    private Sid sid;

    @Transactional
    @Override
    public void createVlog(VlogBO vlogBO) {
        String vid = sid.nextShort();

        Vlogs vlog = new Vlogs();
        BeanUtils.copyProperties(vlogBO, vlog);
        vlog.setId(vid);
        vlog.setLikeCounts(0);
        vlog.setCommentCounts(0);
        vlog.setIsPrivate(YesOrNoEnum.NO.type);

        vlog.setCreateTime(new Date());
        vlog.setCreator(vlogBO.getVloggerId());
        vlog.setModifyTime(new Date());
        vlog.setModifier(vlogBO.getVloggerId());

        vlogsMapper.insert(vlog);
    }
}
