package com.how2java.youyi.service.Impl;

import com.how2java.youyi.mapper.UserMapper;
import com.how2java.youyi.pojo.User;
import com.how2java.youyi.pojo.UserExample;
import com.how2java.youyi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by melon on 18-1-10.
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id desc");
        return userMapper.selectByExample(userExample);
    }
}
