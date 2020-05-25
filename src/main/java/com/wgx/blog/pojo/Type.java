package com.wgx.blog.pojo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Pale language
 * @Description: type实体类
 * @Date:Create: 2020/5/14
 * @since: jdk1.8
 */

@Entity
@Table(name = "tb_type")
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;
    //和blog建立关系  一对多  在被维护端指定维护的属性名  建立明确的关系
    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();


    public Type() {
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
