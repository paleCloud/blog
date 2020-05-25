package com.wgx.blog.controller.admin;

import com.wgx.blog.pojo.Blog;
import com.wgx.blog.pojo.User;
import com.wgx.blog.service.BlogService;
import com.wgx.blog.service.TagService;
import com.wgx.blog.service.TypeService;
import com.wgx.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/14
 * @since: jdk1.8
 */

@Controller
@RequestMapping("admin")
public class BlogController {

    private static final String INPUT = "admin/blog-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 查询所有的博客
     *
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("blogs")
    public String blogs(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.findAllType());
        model.addAttribute("blogs", blogService.listBlog(pageable, blog));
        return LIST;
    }

    /**
     * 带条件查询只刷新博客列表数据
     *
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("blogs/search")
    public String blogsSearch(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                              BlogQuery blog, Model model) {
        model.addAttribute("blogs", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    /**
     * 跳转到新增页面
     *
     * @param model
     * @return
     */
    @GetMapping("blogs/add")
    public String toBlogAdd(Model model) {
        model.addAttribute("blogs", new Blog());
        setTypeAndTag(model);
        return INPUT;
    }

    /**
     * 新增博客
     *
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("blogs")
    public String addBlog(Blog blog, RedirectAttributes attributes, HttpSession session) {
        //设置用户
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.findTypeById(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if (b != null) {
            attributes.addFlashAttribute("msg", "操作成功");
        } else {
            attributes.addFlashAttribute("msg", "操作失败");
        }
        return REDIRECT_LIST;
    }

    /**
     * 跳转到更新界面
     *
     * @param model
     * @return
     */
    @GetMapping("blogs/update/{id}")
    public String toBlogUpdate(@PathVariable Long id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.findBlogById(id);
        blog.init();
        model.addAttribute("blogs", blog);
        return INPUT;
    }

    /**
     * 获取type 和 tag
     *
     * @param model
     */
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.findAllType());
        model.addAttribute("tags", tagService.findAllTag());
    }

    /**
     * 删除博客
     *
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("blogs/delete/{id}")
    public String delBlog(@PathVariable Long id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return REDIRECT_LIST;
    }
}
