package com.wgx.blog.controller.admin;

import com.wgx.blog.pojo.Type;
import com.wgx.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @Author: Pale language
 * @Description: 分类的控制层
 * @Date:Create: 2020/5/16
 * @since: jdk1.8
 */

@Controller
@RequestMapping("admin")
public class TypeController {
    @Autowired
    private TypeService typeService;

    /**
     * 跳转到更新页面并回写数据
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("types/{id}/update")
    public String updateType(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.findTypeById(id));
        return "admin/type-input";
    }

    /**
     * 查询所有(分页)
     *
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("types")
    public String types(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("types", typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 跳转到添加页面
     *
     * @return
     */
    @GetMapping("types/add")
    public String toAddPage(Model model) {
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    /**
     * 添加新的分类
     *
     * @param type
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("types")
    public String addType(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.findTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "该名称已存在");
        }
        if (result.hasErrors()) {
            return "admin/type-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("msg", "添加失败");
        } else {
            attributes.addFlashAttribute("msg", "添加成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 更新分类
     *
     * @param type
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("types/{id}")
    public String updateType(@Valid Type type, BindingResult result, Long id, RedirectAttributes attributes) {
        Type type1 = typeService.findTypeByName(type.getName());
        if (id == null && type1 != null) {
            result.rejectValue("name", "nameError", "该名称已存在");
        }
        if (result.hasErrors()) {
            return "admin/type-input";
        }
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("msg", "更新失败");
        } else {
            attributes.addFlashAttribute("msg", "更新成功");
        }
        return "redirect:/admin/types";
    }

    /**
     * 删除分类
     *
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("msg", "删除成功");
        return "redirect:/admin/types";
    }
}
