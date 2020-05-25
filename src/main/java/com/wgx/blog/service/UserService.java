package com.wgx.blog.service;

import com.wgx.blog.pojo.User;

/**
 * @Author: Pale language
 * @Description: 用户的业务层
 * @Date:Create: 2020/5/14
 * @since: jdk1.8
 */


public interface UserService {
    /**
     * 登录验证
     * @param userName
     * @param password
     * @return
     */
    User login(String userName ,String password);

}
