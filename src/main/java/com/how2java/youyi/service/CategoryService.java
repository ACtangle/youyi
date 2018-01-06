package com.how2java.youyi.service;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.util.Page;

import java.util.List;
/**
 * Created by melon on 18-1-5.
 */
public interface CategoryService {
    List<Category> list(Page page);
    int total();
    void add(Category category);
}
