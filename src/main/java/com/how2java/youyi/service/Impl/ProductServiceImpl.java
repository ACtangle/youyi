package com.how2java.youyi.service.Impl;

import com.how2java.youyi.mapper.ProductMapper;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.pojo.Product;
import com.how2java.youyi.pojo.ProductExample;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by melon on 18-1-8.
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;
    @Autowired
    CategoryService categoryService;
    @Override
    public List<Product> list(){
        ProductExample productExample = new ProductExample();
        productExample.setOrderByClause("id desc");
        return productMapper.selectByExample(productExample);
    }

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int pid) {
        productMapper.deleteByPrimaryKey(pid);
    }

    public void setCategory(List<Product> ps){
        for (Product p : ps)
            setCategory(p);
    }
    public void setCategory(Product p){
        int cid = p.getCid();
        Category c = categoryService.get(cid);
//        p.setCategory(c);
    }
    @Override
    public Product get(int pid) {
        Product p =productMapper.selectByPrimaryKey(pid);
        setCategory(p);
        return p;
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }
}
