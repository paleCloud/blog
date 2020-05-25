package com.wgx.blog.controller.admin;

import com.wgx.blog.pojo.User;
import com.wgx.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 访问全局路径直接跳转到登录页面
     *
     * @return
     */
    @GetMapping
    public String toLoginPage() {
        return "admin/login";
    }

    /**
     * 登录方法
     *
     * @param userName
     * @param password
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("login")
    public String login(@RequestParam String userName,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes, Model model) {
        User user = userService.login(userName, password);
        if (user != null) {
            user.setPassword(null);//前台会显示信息  将隐私信息设为Null
            session.setAttribute("user", user);
            model.addAttribute("user", user);
            return "admin/index";
        } else {
            attributes.addFlashAttribute("message", "用户名或者密码不正确");
            return "redirect:/admin";
        }
    }

    /**
     * 退出
     *
     * @param session
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }

}
