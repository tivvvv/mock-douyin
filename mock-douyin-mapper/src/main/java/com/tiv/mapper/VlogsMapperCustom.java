package com.tiv.mapper;

import com.tiv.model.vo.IndexVlogVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VlogsMapperCustom {

    List<IndexVlogVO> getIndexVlogList(@Param("paramMap") Map<String, Object> map);

    List<IndexVlogVO> getVlogDetailById(@Param("paramMap") Map<String, Object> map);

    List<IndexVlogVO> getLikedVlogList(@Param("paramMap") Map<String, Object> map);

    List<IndexVlogVO> getFollowedVlogList(@Param("paramMap") Map<String, Object> map);

    List<IndexVlogVO> getFriendsVlogList(@Param("paramMap") Map<String, Object> map);

}
