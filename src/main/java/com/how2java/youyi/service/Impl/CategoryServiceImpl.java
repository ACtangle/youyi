package com.how2java.youyi.service.Impl;

import com.how2java.youyi.mapper.CategoryMapper;
import com.how2java.youyi.pojo.Category;
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
        return categoryMapper.list();
    }

    @Override
    public void add(Category category) {
        categoryMapper.add(category);
    }

    @Override
    public void delete(int id) {
        categoryMapper.delete(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.get(id);
    }

    @Override
    public  void update(Category category){
        categoryMapper.update(category);
    }
}
