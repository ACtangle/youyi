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
    public void add(User u) {
        userMapper.insert(u);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User u) {
        userMapper.updateByPrimaryKeySelective(u);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public User get(String name,String password){ return userMapper.selectByUsername(name,password);}

    @Override
    public List<User> list() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id desc");
        return userMapper.selectByExample(userExample);
    }

    @Override
    public Boolean isExist(String name) {
        UserExample example = new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> result = userMapper.selectByExample(example);
        if (!result.isEmpty()){
            return true;
        }
        return false;
    }
}
