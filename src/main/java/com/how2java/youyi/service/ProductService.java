package com.how2java.youyi.service;

import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.pojo.Product;

import java.util.List;

/**
 * Created by melon on 18-1-8.
 */
public interface ProductService {
    List<Product> list(int cid);
    void add(Product product);
    void delete(int pid);
    Product get(int pid);
    void update(Product product);
    void setFirstProductImage(Product product);
    void fill(List<Category> categoryies);
    void fill(Category category);
    void fillByRow(List<Category> categories);
}
