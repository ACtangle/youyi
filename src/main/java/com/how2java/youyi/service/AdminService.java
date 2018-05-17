package com.how2java.youyi.service;

import com.how2java.youyi.pojo.Admin;

/**
 * Created by melon on 18-1-10.
 */
public interface AdminService {
    Admin get(int id);
    Admin get(String username,String password);
    void updatePassword(Admin admin);
}
