package com.how2java.youyi.com.how2java.youyi.service.impl;

import com.how2java.youyi.mapper.CategoryMapper;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.service.CategoryService;
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
    public List<Category> list() {
        return categoryMapper.list();
    }
}
