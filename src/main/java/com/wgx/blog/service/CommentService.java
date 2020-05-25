package com.wgx.blog.service;

import com.wgx.blog.pojo.Comment;

import java.util.List;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/21
 * @since: jdk1.8
 */


public interface CommentService {

    List<Comment> findCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
