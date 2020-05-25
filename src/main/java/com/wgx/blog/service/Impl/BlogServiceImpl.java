package com.wgx.blog.service.Impl;

import com.wgx.blog.exception.NotFoundException;
import com.wgx.blog.mapper.BlogMapper;
import com.wgx.blog.mapper.TagMapper;
import com.wgx.blog.pojo.Blog;
import com.wgx.blog.pojo.Type;
import com.wgx.blog.service.BlogService;
import com.wgx.blog.util.MarkDownUtils;
import com.wgx.blog.util.MyBeanUtils;
import com.wgx.blog.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.util.*;

/**
 * @Author: Pale language
 * @Description:
 * @Date:Create: 2020/5/17
 * @since: jdk1.8
 */

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;

    @Autowired
    private TagMapper tagMapper;

    /**
     * 根据id查询具体的博客
     *
     * @param id
     * @return
     */
    @Override
    public Blog findBlogById(Long id) {
        return blogMapper.getOne(id);
    }

    /**
     * 多条件查询博客
     *
     * @param pageable
     * @param blog
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {

        return blogMapper.findAll(new Specification<Blog>() {
            /**
             *  添加分类条件的查询
             * @param root  指的要查询的哪一个对象
             * @param query  查询条件的容器
             * @param criteriaBuilder  设置具体条件的表达式
             * @return
             */
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(blog.getTitle())) {
                    //判断标题是否为空  不为空就添加
                    predicates.add(criteriaBuilder.like(root.get("title"), "%" + blog.getTitle() + "%"));
                }
                if (blog.getTypeId() != null) {
                    //判断分类是否为空 不空添加   这个地方拿到的其实是分类的id
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if (blog.isRecommend()) {
                    //判断是否推荐   如果是添加条件
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    /**
     * 查询具体博客
     *
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Blog findAndConvert(Long id) {
        Blog blog = blogMapper.getOne(id);
        if (blog == null) {
            throw new NotFoundException("没有找到指定的博客");
        }
       /* 直接处理的话可能会操作数据库
       String content = blog.getContent();
        blog.setContent(MarkDownUtils.markdownToHtmlExtensions(content));
        */
        //创建一个blog  然后将获取到的blog复制给新的Blog 然后将新的blog返回  这样保证了数据库的原有数据不会发生改变
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkDownUtils.markdownToHtmlExtensions(content));
        blogMapper.updateViews(id);
        return b;
    }

    /**
     * 分页查询博客
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogMapper.findAll(pageable);
    }

    /**
     * 根据标签id查询博客
     * @param tagId
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(Long tagId, Pageable pageable) {
        return blogMapper.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join join = root.join("tags");
                return criteriaBuilder.equal(join.get("id"),tagId);
            }
        }, pageable);
    }

    /**
     * 根据标题或者描述查询博客
     *
     * @param query
     * @param pageable
     * @return
     */
    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogMapper.findBlogByQuery(query, pageable);
    }

    /**
     * 查询最新的推荐博客
     *
     * @param size
     * @return
     */
    @Override
    public List<Blog> findBlogTop(Integer size) {
        return blogMapper.findTop(PageRequest.of(0, size, Sort.by(Sort.Direction.DESC, "updateTime")));
    }

    /**
     * 根据年份分组博客
     * @return
     */
    @Override
    public Map<String, List<Blog>> archivesBlog() {
        List<String> years = blogMapper.findAllYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year,blogMapper.findBlogByYear(year));
        }
        return map;
    }

    /**
     * 查询博客的数量
     * @return
     */
    @Override
    public Long findBlogCount() {
        return blogMapper.count();
    }

    /**
     * 保存博客
     *
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        //前端传过数据库没有标签  在数据库新增标签   此处报异常
    /*    if(blog.getTagIds().contains("null")){
            tagMapper.save(blog.getTags().get(0));
        }*/
        //初始化参数
        if (blog.getId() == null) {
            blog.setCreatTime(new Date());
            blog.setUpdateTime(blog.getCreatTime());
            blog.setViews(0);
        } else {
            blog.setUpdateTime(new Date());
        }
        return blogMapper.save(blog);
    }

    /**
     * 更新博客
     *
     * @param id
     * @param blog
     * @return
     */
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Blog one = blogMapper.getOne(id);
        if (one == null) {
            throw new NotFoundException("没有该博客");
        }
        BeanUtils.copyProperties(blog, one, MyBeanUtils.getNullPropertyNames(blog));
        one.setCreatTime(blog.getCreatTime());
        one.setUpdateTime(new Date());
        return blogMapper.save(one);
    }

    /**
     * 删除博客
     *
     * @param id
     */
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteById(id);
    }
}
