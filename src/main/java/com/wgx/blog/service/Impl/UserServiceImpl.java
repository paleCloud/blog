package com.wgx.blog.service.Impl;

import com.wgx.blog.mapper.UserMapper;
import com.wgx.blog.pojo.User;
import com.wgx.blog.service.UserService;
import com.wgx.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/14
 * @since: jdk1.8
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录验证
     * @param userName
     * @param password
     * @return
     */
    @Override
    public User login(String userName, String password) {
        return userMapper.findUserByUserNameAndPassword(userName, MD5Utils.code(password));
    }
}
