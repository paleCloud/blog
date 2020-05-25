package com.wgx.blog.controller.admin;

import com.wgx.blog.pojo.Tag;
import com.wgx.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @Author: Pale language
 * @Description: 标签的控制层
 * @Date:Create: 2020/5/17
 * @since: jdk1.8
 */

@Controller
@RequestMapping("admin")
public class TagController {
    @Autowired
    private TagService tagService;

    /**
     * 跳转更新标签
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("tags/update/{id}")
    public String toUpdateTag(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.findTagById(id));
        return "admin/tag-input";
    }

    /**
     * 查询所有(分页)
     *
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("tags")
    public String tags(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("tags", tagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @GetMapping("tags/add")
    public String toAddPage(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag-input";
    }

    /**
     * 添加新的标签
     *
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("tags")
    public String addTag(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.findTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "该名称已存在");
        }
        if (result.hasErrors()) {
            return "admin/tag-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("msg", "添加失败");
        } else {
            attributes.addFlashAttribute("msg", "添加成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 更新分类
     *
     * @param tag
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("tags/{id}")
    public String updateTag(@Valid Tag tag, BindingResult result, Long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.findTagByName(tag.getName());
        if (id == null && tag1 != null) {
            result.rejectValue("name", "nameError", "该名称已存在");
        }
        if (result.hasErrors()) {
            return "admin/tag-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("msg", "更新失败");
        } else {
            attributes.addFlashAttribute("msg", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除标签
     *
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("tags/delete/{id}")
    public String deleteTag(@PathVariable Long id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/tags";
    }
}
