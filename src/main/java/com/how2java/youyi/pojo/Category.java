package com.how2java.youyi.pojo;

/**
 * Created by melon on 18-1-5.
 */
public class Category {

    private Integer id;
    private String name;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(String name) {
        //trim方法去掉属性字段左右边缘的空格或者制表符
        this.name = name == null?null:name.trim();
    }
}
