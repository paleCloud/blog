package com.wgx.blog.controller;

import com.wgx.blog.pojo.Tag;
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

import java.util.List;

/**
 * @Author: Pale language
 * @Description:  前端标签的控制层
 * @Date:Create: 2020/5/22
 * @since: jdk1.8
 */

@Controller
public class TagShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;
    /**
     * 第一次访问标签页面时传-1
     * @return
     */
    @GetMapping("tags/{id}")
    public String tags(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, @PathVariable Long id, Model model){
        List<Tag> tags = tagService.findAllTag();
        if(id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags",tags);
        model.addAttribute("blogs",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }

}
