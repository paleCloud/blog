package com.wgx.blog.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Pale language
 * @Description:  处理博客中的空字段
 * @Date:Create: 2020/5/19
 * @since: jdk1.8
 */


public class MyBeanUtils {
    /**
     * 过滤博客中的空字段属性
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames(Object source){
        BeanWrapper bw = new BeanWrapperImpl(source);
        PropertyDescriptor[] descriptor = bw.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor pd : descriptor) {
            String name = pd.getName();
            if(bw.getPropertyValue(name) == null){
                nullPropertyNames.add(name);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
