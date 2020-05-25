package com.wgx.blog.mapper;

import com.wgx.blog.pojo.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/16
 * @since: jdk1.8
 */


public interface TypeMapper extends JpaRepository<Type,Long> {
    /**
     * 根据名字查询分类
     * @param name
     * @return
     */
    Type findTypeByName(String name);

    /**
     * 使用分页来查询  因为分页自带排序
     * @param pageable
     * @return
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable);
}
