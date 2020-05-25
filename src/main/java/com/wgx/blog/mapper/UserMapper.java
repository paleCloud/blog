package com.wgx.blog.mapper;

import com.wgx.blog.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/14
 * @since: jdk1.8
 */


public interface UserMapper extends JpaRepository<User,Long> {
    /**
     * 登录验证
     * @param userName
     * @param password
     * @return
     */
    User findUserByUserNameAndPassword(String userName,String password);
}
