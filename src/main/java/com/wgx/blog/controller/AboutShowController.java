package com.wgx.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/22
 * @since: jdk1.8
 */

@Controller
public class AboutShowController {

    @GetMapping("about")
    public String about(){
        return "about";
    }
}
