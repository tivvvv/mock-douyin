package com.tiv.service.impl;

import com.github.pagehelper.PageHelper;
import com.tiv.common.constant.Constants;
import com.tiv.common.enums.YesOrNoEnum;
import com.tiv.common.result.PagedResult;
import com.tiv.mapper.LikeVlogsMapper;
import com.tiv.mapper.VlogsMapper;
import com.tiv.mapper.VlogsMapperCustom;
import com.tiv.model.bo.VlogBO;
import com.tiv.model.pojo.LikeVlogs;
import com.tiv.model.pojo.Vlogs;
import com.tiv.model.vo.IndexVlogVO;
import com.tiv.service.VlogService;
import com.tiv.service.base.BaseInfoProperties;
import com.tiv.service.utils.RedisUtil;
import com.tiv.service.utils.idworker.Sid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VlogServiceImpl extends BaseInfoProperties implements VlogService {

    @Autowired
    private VlogsMapper vlogsMapper;

    @Autowired
    private VlogsMapperCustom vlogsMapperCustom;

    @Autowired
    private LikeVlogsMapper likeVlogsMapper;

    @Autowired
    private Sid sid;
    @Autowired
    private RedisUtil redisUtil;

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

    @Override
    public PagedResult<IndexVlogVO> getIndexVlogList(String userId, String search, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(search)) {
            map.put("search", search);
        }
        List<IndexVlogVO> list = vlogsMapperCustom.getIndexVlogList(map);
        for (IndexVlogVO vlog : list) {
            String vlogId = vlog.getVlogId();
            vlog.setIsLiked(isVlogLiked(userId, vlogId));
        }
        return buildPagedResult(list, page);
    }

    private boolean isVlogLiked(String userId, String vlogId) {
        String s = redisUtil.get(Constants.USER_LIKE_VLOG_PREFIX + userId + ":" + vlogId);
        return StringUtils.isNotBlank(s) && "1".equalsIgnoreCase(s);
    }

    @Override
    public IndexVlogVO getVlogDetailById(String userId, String vlogId) {
        Map<String, Object> map = new HashMap<>();
        map.put("vlogId", vlogId);

        List<IndexVlogVO> list = vlogsMapperCustom.getVlogDetailById(map);

        if (!CollectionUtils.isEmpty(list)) {
            return list.get(0);
        }

        return null;
    }

    @Transactional
    @Override
    public void changePrivate(String userId, String vlogId, Integer isPrivate) {
        Example example = new Example(Vlogs.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", vlogId);
        criteria.andEqualTo("vloggerId", userId);

        Vlogs pending = new Vlogs();
        pending.setIsPrivate(isPrivate);

        vlogsMapper.updateByExampleSelective(pending, example);

    }

    @Override
    public PagedResult<Vlogs> queryMyVlogList(String userId, Integer isPrivate, Integer page, Integer pageSize) {
        Example example = new Example(Vlogs.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("vloggerId", userId);
        criteria.andEqualTo("isPrivate", isPrivate);

        PageHelper.startPage(page, pageSize);
        List<Vlogs> vlogs = vlogsMapper.selectByExample(example);
        return buildPagedResult(vlogs, page);
    }

    @Override
    public void likeVlog(String userId, String vlogId) {
        String likeId = sid.nextShort();

        LikeVlogs likeVlogs = LikeVlogs.builder()
                .id(likeId)
                .vlogId(vlogId)
                .userId(userId).build();
        likeVlogsMapper.insert(likeVlogs);
    }

    @Override
    public void unlikeVlog(String userId, String vlogId) {
        LikeVlogs likeVlogs = LikeVlogs.builder()
                .vlogId(vlogId)
                .userId(userId).build();
        likeVlogsMapper.delete(likeVlogs);
    }
}
