package com.how2java.youyi.service.Impl;

import com.how2java.youyi.mapper.CategoryMapper;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.pojo.CategoryExample;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by melon on 18-1-5.
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

//    @Override
//    public List<Category> list(Page page) {
//        return categoryMapper.list(page);
//    }
//
//    @Override
//    public int total() {
//        return categoryMapper.total();
//    }

    @Override
    public List<Category> list(){
        CategoryExample example = new CategoryExample();
        example.setOrderByClause("id desc");
        return categoryMapper.selectByExample(example);
    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category get(int id)
    {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public  void update(Category category){
        categoryMapper.updateByPrimaryKeySelective(category);
    }
}
