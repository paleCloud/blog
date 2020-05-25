package com.wgx.blog.service.Impl;

import com.wgx.blog.mapper.CommentMapper;
import com.wgx.blog.pojo.Comment;
import com.wgx.blog.service.CommentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: Pale language
 * @Description: 评论的实现类
 * @Date:Create: 2020/5/21
 * @since: jdk1.8
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    /**
     * 根据博客id查询该博客下的所有评论
     * @param blogId
     * @return
     */
    @Override
    public List<Comment> findCommentByBlogId(Long blogId) {
        return eachComment(commentMapper.findCommentByBlogIdAndParentCommentNull(blogId, Sort.by( "creatTime")));

    }

    /**
     * 保存评论
     * @param comment
     * @return
     */
    @Transactional(rollbackFor = {Throwable.class})
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId = comment.getParentComment().getId();
        if (parentCommentId != -1) {
            comment.setParentComment(commentMapper.getOne(parentCommentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreatTime(new Date());
        return commentMapper.save(comment);
    }

    /**
     * 循环遍历所有顶级评论
     * @param comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }

    /**
     *
     * @param comments root根节点，blog不为空的对象集合
     * @return
     */
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replays1 = comment.getReplyComments();
            for(Comment replay1 : replays1) {
                //循环迭代，找出子代，存放在tempReplays中
                recursively(replay1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempRepays);
            //清除临时存放区
            tempRepays = new ArrayList<>();
        }
    }

    /**
     * 存放迭代找出的所有子代的集合
     */
    private List<Comment> tempRepays = new ArrayList<>();
    /**
     * 递归迭代，剥洋葱
     * @param comment 被迭代的对象
     * @return
     */
    private void recursively(Comment comment) {
        //顶节点添加到临时存放集合
        tempRepays.add(comment);
        if (comment.getReplyComments().size()>0) {
            List<Comment> repays = comment.getReplyComments();
            for (Comment reply : repays) {
                tempRepays.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }
}
