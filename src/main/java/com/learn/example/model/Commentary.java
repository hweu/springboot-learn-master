package com.learn.example.model;

import java.io.Serializable;

public class Commentary implements Serializable {
    /**
     * 
     */
    private Integer id;

    /**
     * 
     */
    private Integer article_id;

    /**
     * 评论
     */
    private String comment;

    /**
     * 
     * @return id 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return article_id 
     */
    public Integer getArticle_id() {
        return article_id;
    }

    /**
     * 
     * @param article_id 
     */
    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }

    /**
     * 评论
     * @return comment 评论
     */
    public String getComment() {
        return comment;
    }

    /**
     * 评论
     * @param comment 评论
     */
    public void setComment(String comment) {
        this.comment = comment == null ? null : comment.trim();
    }
}