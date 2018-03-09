package com.how2java.youyi.service;

import com.how2java.youyi.pojo.Review;

import java.util.List;

/**
 * Created by melon on 18-3-8.
 */

public interface ReviewService {
    void add(Review c);

    void delete(int id);
    void update(Review c);
    Review get(int id);
    List list(int pid);

    int getCount(int pid);
}
