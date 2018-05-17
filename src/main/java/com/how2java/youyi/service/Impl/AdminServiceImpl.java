package com.how2java.youyi.service.Impl;

import com.how2java.youyi.mapper.AdminMapper;
import com.how2java.youyi.pojo.Admin;
import com.how2java.youyi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by melon on 18-1-10.
 */
@Service
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin get(int id) {
        return adminMapper.selectByPrimaryKey(id);
    }

    @Override
    public Admin get(String username,String password) {
        return adminMapper.selectByUsername(username,password);
    }

    @Override
    public void updatePassword(Admin admin) {
        adminMapper.updateByPrimaryKeySelective(admin);
    }
}
