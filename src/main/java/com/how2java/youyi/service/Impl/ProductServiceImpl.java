package com.how2java.youyi.service.Impl;

import com.how2java.youyi.mapper.ProductMapper;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.pojo.Product;
import com.how2java.youyi.pojo.ProductExample;
import com.how2java.youyi.pojo.ProductImage;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.service.ProductImageService;
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
    @Autowired
    ProductImageService productImageService;

    public List<Product> list(int cid){
        ProductExample productExample = new ProductExample();
        productExample.setOrderByClause("id desc");
        List result = productMapper.selectByExample(productExample);
        setCategory(result);
        setFirstProductImage(result);
        return result;
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
        setFirstProductImage(p);
        return p;
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public void setFirstProductImage(Product product) {
        List<ProductImage> pis = productImageService.list(product.getId(),ProductImageService.type_single);
        if(!pis.isEmpty()) {
            ProductImage productImage = pis.get(0);
            product.setFirstProductImage(productImage);
        }
    }

    public void setFirstProductImage(List<Product> ps) {
        for (Product p : ps) {
            setFirstProductImage(p);
        }
    }
}
