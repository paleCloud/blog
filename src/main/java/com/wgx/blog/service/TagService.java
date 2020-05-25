package com.wgx.blog.service;

import com.wgx.blog.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/16
 * @since: jdk1.8
 */


public interface TagService {

    Tag saveTag(Tag tag);

    Tag findTagById(Long id);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> findAllTag();

    List<Tag> listTag(String ids);

    Tag updateTag(Long id ,Tag tag);

    void deleteTag(Long id);

    Tag findTagByName(String name);

    List<Tag> findTagTop(Integer size);
}
