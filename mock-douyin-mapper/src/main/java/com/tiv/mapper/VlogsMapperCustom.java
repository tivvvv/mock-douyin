package com.tiv.mapper;

import com.tiv.model.vo.IndexVlogVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface VlogsMapperCustom {

    List<IndexVlogVO> getIndexVlogList(@Param("paramMap") Map<String, Object> map);
}
