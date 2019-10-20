package com.learn.example.dao;

import com.learn.example.model.Commentary;
import org.apache.ibatis.annotations.Param;

public interface CommentaryMapper {
    int insert(Commentary record);

    int insertSelective(Commentary record);

    int updateCommentary(Commentary commentary);

    int deleteCommentary(Integer id);

    Commentary findById(@Param("id") Integer id);


}