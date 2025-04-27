package com.tiv.mapper;

import com.tiv.model.vo.FanVO;
import com.tiv.model.vo.VloggerVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FansMapperCustom {

    List<VloggerVO> getFollows(@Param("paramMap") Map<String, Object> map);

    List<FanVO> getFans(@Param("paramMap") Map<String, Object> map);

}