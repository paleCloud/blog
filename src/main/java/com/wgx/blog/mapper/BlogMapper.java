package com.wgx.blog.mapper;

import com.wgx.blog.pojo.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/17
 * @since: jdk1.8
 */


public interface BlogMapper extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {

    /**
     * 查询推荐的博客
     *
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.recommend=true")
    List<Blog> findTop(Pageable pageable);

    /**
     * 多条件搜索博客
     *
     * @param query
     * @param pageable
     * @return
     */
    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findBlogByQuery(String query, Pageable pageable);

    /**
     * 更新浏览次数
     *
     * @param id
     * @return
     */
    @Modifying
    @Query("update Blog b set b.views = b.views+1 where b.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format',b.creatTime,'%Y') as year from Blog b group by function('date_format',b.creatTime,'%Y') order by year desc")
    List<String> findAllYear();

    @Query("select b from Blog b  where function('date_format',b.creatTime,'%Y')= ?1")
    List<Blog> findBlogByYear(String year);
}
