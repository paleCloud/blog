package com.wgx.blog.service;

import com.wgx.blog.pojo.Blog;
import com.wgx.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/17
 * @since: jdk1.8
 */


public interface BlogService {
    Blog findBlogById(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Blog findAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Long tagId,Pageable pageable);

    Page<Blog> listBlog(String query, Pageable pageable);

    List<Blog> findBlogTop(Integer size);

    Map<String,List<Blog>> archivesBlog();

    Long findBlogCount();

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id, Blog blog);

    void deleteBlog(Long id);


}
