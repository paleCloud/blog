package com.wgx.blog.mapper;

import com.wgx.blog.pojo.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Pale language
 * @Description: 评论
 * @Date:Create: 2020/5/17
 * @since: jdk1.8
 */


public interface CommentMapper extends JpaRepository<Comment, Long> {

    /**
     * 判断评论是否为顶级
     * @param blogId
     * @param sort
     * @return
     */
    List<Comment> findCommentByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
