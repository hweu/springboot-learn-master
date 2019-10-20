package com.learn.example.service.impl;

import com.learn.example.dao.CommentaryMapper;
import com.learn.example.model.Commentary;
import com.learn.example.service.CommentaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class CommentaryServiceImpl implements CommentaryService {
    @Autowired
    private  CommentaryMapper commentaryMapper;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 获取评论逻辑：
     * 如果缓存存在，从缓存中获取评论信息
     * 如果缓存不存在，从 DB 中获取评论信息，然后插入缓存
     */
    @Override
    public Commentary findCommentaryById(Integer id) {
        ValueOperations<String, Commentary> operations = redisTemplate.opsForValue();
        String key = "commentary_" + id;
        //缓存存在
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            Commentary commentary = operations.get(key);
            return commentary;
        }
        //从DB中获取评论信息
        Commentary commentary = commentaryMapper.findById(id);
        operations.set(key,commentary,10, TimeUnit.SECONDS);
        return commentary;
    }

    @Override
    public int addComentary(Commentary commentary) {
        return commentaryMapper.insertSelective(commentary);
    }

    /**
     * 更新评论逻辑：
     * 如果缓存存在，删除
     * 如果缓存不存在，不操作
     */
    @Override
    public int updateCommentary(Commentary commentary) {
        //如果缓存存在删除缓存
        String key = "commentary_" + commentary.getId();
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            redisTemplate.delete(key);
        }

        return commentaryMapper.updateCommentary(commentary);
    }

    @Override
    public int deleteCommentary(Integer id) {
        //如果缓存存在删除缓存
        String key = "commentary_" + id;
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            redisTemplate.delete(key);
        }
        return commentaryMapper.deleteCommentary(id);
    }
}
