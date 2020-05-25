package com.wgx.blog.controller;

import com.wgx.blog.service.BlogService;
import com.wgx.blog.service.TagService;
import com.wgx.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author: Pale language
 * @Description: 前端首页的控制层
 * @Date:Create: 2020/5/13
 * @since: jdk1.8
 */

@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    /**
     * 根据标题或者是描述搜索博客
     * @param pageable
     * @param query
     * @param model
     * @return
     */
    @PostMapping("search")
    public String search(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                     Pageable pageable, @RequestParam String query, Model model){
        model.addAttribute("blogs",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute("query",query);
        return "search";
    }
    /**
     * 首页页面
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("blogs", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.findTypeTop(6));
        model.addAttribute("tags", tagService.findTagTop(10));
        model.addAttribute("blogsTop", blogService.findBlogTop(8));
        return "index";
    }


    /**
     * 查看具体的博客
     * @return
     */
    @GetMapping("blog/{id}")
    public String blog(@PathVariable Long id,Model model) {
        model.addAttribute("blog",blogService.findAndConvert(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        model.addAttribute("newBlogs",blogService.findBlogTop(3));
        return "_fragments :: newBlogList";
    }
}
