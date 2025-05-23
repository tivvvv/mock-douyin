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
import com.tiv.service.FanService;
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
    private FanService fanService;

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

            vlog.setIsVloggerFollowed(fanService.queryFollowStatus(userId, vlog.getVloggerId()));
            // 视频是否已被当前用户点赞
            vlog.setIsLiked(isVlogLiked(userId, vlogId));
            // 视频总点赞数
            vlog.setLikeCounts(getVlogLikeCounts(vlogId));
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
            IndexVlogVO indexVlogVO = list.get(0);

            indexVlogVO.setIsVloggerFollowed(true);
            // 视频是否已被当前用户点赞
            indexVlogVO.setIsLiked(isVlogLiked(userId, vlogId));
            // 视频总点赞数
            indexVlogVO.setLikeCounts(getVlogLikeCounts(vlogId));
            return indexVlogVO;
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

    @Override
    public Integer getVlogLikeCounts(String vlogId) {
        String countsStr = redisUtil.get(Constants.VLOG_LIKE_COUNTS_PREFIX + vlogId);
        return StringUtils.isBlank(countsStr) ? 0 : Integer.parseInt(countsStr);
    }

    @Override
    public PagedResult<IndexVlogVO> getLikedVlogList(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<IndexVlogVO> likedVlogList = vlogsMapperCustom.getLikedVlogList(map);
        return buildPagedResult(likedVlogList, page);
    }

    @Override
    public PagedResult<IndexVlogVO> getFollowedVlogList(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<IndexVlogVO> likedVlogList = vlogsMapperCustom.getFollowedVlogList(map);

        for (IndexVlogVO vlog : likedVlogList) {
            String vlogId = vlog.getVlogId();

            vlog.setIsVloggerFollowed(true);
            // 视频是否已被当前用户点赞
            vlog.setIsLiked(isVlogLiked(userId, vlogId));
            // 视频总点赞数
            vlog.setLikeCounts(getVlogLikeCounts(vlogId));
        }
        return buildPagedResult(likedVlogList, page);
    }

    @Override
    public PagedResult<IndexVlogVO> getFriendsVlogList(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        List<IndexVlogVO> likedVlogList = vlogsMapperCustom.getFriendsVlogList(map);

        for (IndexVlogVO vlog : likedVlogList) {
            String vlogId = vlog.getVlogId();

            vlog.setIsVloggerFollowed(true);
            // 视频是否已被当前用户点赞
            vlog.setIsLiked(isVlogLiked(userId, vlogId));
            // 视频总点赞数
            vlog.setLikeCounts(getVlogLikeCounts(vlogId));
        }
        return buildPagedResult(likedVlogList, page);
    }
}
