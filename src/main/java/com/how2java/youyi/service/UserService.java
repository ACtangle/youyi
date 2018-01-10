package com.how2java.youyi.service;

import com.how2java.youyi.pojo.User;

import java.util.List;

/**
 * Created by melon on 18-1-10.
 */
public interface UserService {
    void add(User c);

    void delete(int id);

    void update(User c);

    User get(int id);

    List list();
}
