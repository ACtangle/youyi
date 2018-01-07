package com.how2java.youyi.mapper;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.util.Page;

import java.util.List;
/**
 * Created by melon on 18-1-5.
 */

public interface CategoryMapper {
     List<Category> list(Page page);
     int total();
     void add(Category category);
     void delete(int id);
}
