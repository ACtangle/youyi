package com.how2java.youyi.service;

import com.how2java.youyi.pojo.Product;
import com.how2java.youyi.pojo.PropertyValue;

import java.util.List;

/**
 * Created by melon on 18-1-9.
 */
public interface PropertyValueService {

    void init(Product product);
    void update(PropertyValue propertyValue);
    PropertyValue get(int ptid,int pid);
    List<PropertyValue> list(int pid);
}
