package com.how2java.youyi.service;

import com.how2java.youyi.pojo.Property;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by melon on 18-1-7.
 */
public interface PropertyService {
    void add(Property property);
    void delete(int id );
    void update(Property property);
    Property get(int id);
    List list(int cid);
}
