package com.how2java.youyi.service;




import com.how2java.youyi.pojo.ProductImage;

import java.util.List;

/**
 * Created by melon on 18-1-8.
 */
public interface ProductImageService {
    String type_single = "type_single";
    String type_detail = "type_detail";


    List list(int pid,String type);
    void add(ProductImage productImage);
    void delete(int id);
    ProductImage get(int id);
}
