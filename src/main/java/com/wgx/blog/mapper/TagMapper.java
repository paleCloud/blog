package com.wgx.blog.mapper;

import com.wgx.blog.pojo.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Pale language
 * @Description:  标签dao
 * @Date:Create: 2020/5/16
 * @since: jdk1.8
 */


public interface TagMapper extends JpaRepository<Tag,Long> {
    /**
     * 根据名字查询标签
     * @param name
     * @return
     */
    Tag findTagByName(String name);

    /**
     * 使用分页来查询  因为分页自带排序
     * @param pageable
     * @return
     */
    @Query("select t from Tag t")
    List<Tag> findTop(Pageable pageable);
}
