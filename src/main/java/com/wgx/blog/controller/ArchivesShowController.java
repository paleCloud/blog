package com.wgx.blog.controller;

import com.wgx.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Pale language
 * @Description: 归档的控制层
 * @Date:Create: 2020/5/22
 * @since: jdk1.8
 */

@Controller
public class ArchivesShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("archives")
    public String archives(Model model){
        model.addAttribute("archivesBlogs",blogService.archivesBlog());
        model.addAttribute("blogCount",blogService.findBlogCount());
        return "archives";
    }

}
