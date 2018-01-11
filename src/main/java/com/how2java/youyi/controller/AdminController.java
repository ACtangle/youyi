package com.how2java.youyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.youyi.pojo.Admin;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.service.AdminService;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by melon on 18-1-10.
 */
@Controller
@RequestMapping("")
public class AdminController {

    @Autowired
    AdminService adminService;
    @Autowired
    CategoryController categoryController;

    @RequestMapping("adminLogin")
    public String display(){
        return "admin/adminLogin";
    }

    @RequestMapping("admin_login")
    public String login(String username,Model model, HttpSession session,Page page) {
        Admin admin = adminService.get(username);
//        if( username != null && username.equals(admin.getUsername()) && password !=null && password.equals(admin.getPassword())) {
//
//        }
        session.setAttribute("admin",admin);
        categoryController.list(model,page);
        return "admin/listCategory";
    }
}
