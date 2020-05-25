package com.wgx.blog.service;

import com.wgx.blog.pojo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/16
 * @since: jdk1.8
 */


public interface TypeService {

    Type saveType(Type type);

    Type findTypeById(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id ,Type type);

    void deleteType(Long id);

    Type findTypeByName(String name);

    List<Type> findAllType();

    List<Type> findTypeTop(Integer size);
}
