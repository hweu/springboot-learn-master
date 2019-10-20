package com.learn.example.service;

import com.learn.example.model.Commentary;

public interface CommentaryService {

    /**
     * 根据评论id获取评论信息
     * @param id
     * @return
     */
    Commentary findCommentaryById(Integer id);

    /**
     * 新增评论信息
     * @param commentary
     * @return
     */
    int addComentary(Commentary commentary);

    /**
     * 更新评论信息
     * @param commentary
     * @return
     */
    int updateCommentary(Commentary commentary);

    /**
     * 根据评论id删除评论信息
     * @param id
     * @return
     */
    int deleteCommentary(Integer id);
}
