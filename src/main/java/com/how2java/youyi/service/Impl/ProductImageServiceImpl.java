package com.how2java.youyi.service.Impl;

import com.how2java.youyi.mapper.ProductImageMapper;
import com.how2java.youyi.pojo.ProductImage;
import com.how2java.youyi.pojo.ProductImageExample;
import com.how2java.youyi.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by melon on 18-1-8.
 */
@Service
public class ProductImageServiceImpl implements ProductImageService{

    @Autowired
    ProductImageMapper productImageMapper;



    @Override
    public List list(int pid , String type) {
        ProductImageExample productImageExample = new ProductImageExample();
        productImageExample.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        productImageExample.setOrderByClause("id desc");
        return productImageMapper.selectByExample(productImageExample);
    }

    @Override
    public void add(ProductImage productImage) {
        productImageMapper.insert(productImage);
    }

    @Override
    public void delete(int id){
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

//    public List list(int pid , String type,String sort) {
//        ProductImageExample productImageExample = new ProductImageExample();
//        productIma eExample.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
//        if("date".equals(sort)){
//            productImageExample.setOrderByClause("createDate desc");
////        }else if("review".equals(sort)){
////            productImageExample.setOrderByClause();
//        }else if("saleCount".equals(sort)){
//            productImageExample.setOrderByClause();
//        }else if("price".equals(sort)){
//
//        }else if("all".equals(sort)){
//
//        }
//
//        return productImageMapper.selectByExample(productImageExample);
//    }
}
