package com.wgx.blog.service.Impl;

import com.wgx.blog.exception.NotFoundException;
import com.wgx.blog.mapper.TagMapper;
import com.wgx.blog.pojo.Tag;
import com.wgx.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Pale language
 * @Description:  类型的业务层
 * @Date:Create: 2020/5/16
 * @since: jdk1.8
 */

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    /**
     * 新增标签
     * @param tag
     * @return
     */
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagMapper.save(tag);
    }

    /**
     * 根据id查询具体标签
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Tag findTagById(Long id) {
        return tagMapper.getOne(id);
    }

    /**
     * 分页查询标签
     * @param pageable
     * @return
     */
    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagMapper.findAll(pageable);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Tag> findAllTag() {
        return tagMapper.findAll();
    }

    /**
     * 根据多个id查询标签
     * @param ids
     * @return
     */
    @Override
    public List<Tag> listTag(String ids) {
        return tagMapper.findAllById(convertToList(ids));
    }

    /**
     * 将前端传过来的ids转换为list
      * @param ids
     * @return
     */
    private List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids != null){
            String[] array = ids.split(",");
            for (int i = 0; i < array.length; i++) {
                list.add(new Long(array[i]));
            }
        }
        return list;
    }

    /**
     * 更新标签
     * @param id
     * @param tag
     * @return
     */
    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagMapper.getOne(id);
        if(t == null){
            throw new NotFoundException("没有该对象");
        }
        BeanUtils.copyProperties(tag,t);
        return tagMapper.save(t);
    }

    /**
     * 删除标签
     * @param id
     */
    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagMapper.deleteById(id);
    }

    /**
     * 根据名字查询标签
     * @param name
     * @return
     */
    @Override
    public Tag findTagByName(String name) {
        return tagMapper.findTagByName(name);
    }

    /**
     * 查询博客标签最多的十个标签数
     * @param size
     * @return
     */
    @Override
    public List<Tag> findTagTop(Integer size) {
        return tagMapper.findTop(PageRequest.of(0,size,Sort.by(Sort.Direction.DESC,"blogs.size")));
    }
}
