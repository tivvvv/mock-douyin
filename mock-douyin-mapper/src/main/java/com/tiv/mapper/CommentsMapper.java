package com.tiv.mapper;

import com.tiv.mapper.my.MyMapper;
import com.tiv.model.pojo.Comments;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsMapper extends MyMapper<Comments> {
}