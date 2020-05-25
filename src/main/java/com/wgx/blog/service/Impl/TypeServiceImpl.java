package com.wgx.blog.service.Impl;

import com.wgx.blog.exception.NotFoundException;
import com.wgx.blog.mapper.TypeMapper;
import com.wgx.blog.pojo.Type;
import com.wgx.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Pale language
 * @Description:  类型的业务层
 * @Date:Create: 2020/5/16
 * @since: jdk1.8
 */

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    /**
     * 新增类型
     * @param type
     * @return
     */
    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeMapper.save(type);
    }

    /**
     * 根据id查询具体类型
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Type findTypeById(Long id) {
        return typeMapper.getOne(id);
    }

    /**
     * 分页查询类型
     * @param pageable
     * @return
     */
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeMapper.findAll(pageable);
    }

    /**
     * 更新类型
     * @param id
     * @param type
     * @return
     */
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeMapper.getOne(id);
        if(t == null){
            throw new NotFoundException("没有该对象");
        }
        BeanUtils.copyProperties(type,t);
        return typeMapper.save(t);
    }

    /**
     * 删除类型
     * @param id
     */
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeMapper.deleteById(id);
    }

    @Override
    public Type findTypeByName(String name) {
        return typeMapper.findTypeByName(name);
    }

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Type> findAllType() {
        return typeMapper.findAll();
    }

    /**
     * 获取分类下博客最多数量的前六个
     * @param size
     * @return
     */
    @Override
    public List<Type> findTypeTop(Integer size) {
        return typeMapper.findTop(PageRequest.of(0,size,Sort.by(Sort.Direction.DESC,"blogs.size")));
    }
}
