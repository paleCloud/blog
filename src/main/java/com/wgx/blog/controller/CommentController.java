package com.wgx.blog.controller;

import com.wgx.blog.pojo.Comment;
import com.wgx.blog.pojo.User;
import com.wgx.blog.service.BlogService;
import com.wgx.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

/**
 * @Author: Pale language
 * @Description: 评论控制层
 * @Date:Create: 2020/5/21
 * @since: jdk1.8
 */

@Controller
public class CommentController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 获取所有的评论信息
     *
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.findCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    /**
     * 保存评论
     *
     * @param comment
     * @return
     */
    @PostMapping("comments")
    public String post(Comment comment, HttpSession session) {
        comment.setBlog(blogService.findBlogById(comment.getBlog().getId()));
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        }else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlog().getId();
    }
}
