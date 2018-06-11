package com.how2java.youyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.youyi.pojo.Admin;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.service.AdminService;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @RequestMapping("adminPasswordUpdate")
    public String displayAdminUpdate() {
        return "admin/adminUpdate";
    }

    @RequestMapping(value="admin_login",method=RequestMethod.POST)
    public String login(String username, String password, Model model, HttpSession session, Page page, HttpServletRequest request , HttpServletResponse response) throws Exception{
        Admin admin  = new Admin();
        if(username != null && password != null){
            admin.setUsername(username);
            admin.setPassword(password);
            admin = adminService.get(username,password);
            if (admin != null) {
                session.setAttribute("admin",admin);
                categoryController.list(model,page);
//                return "admin/listCategory";
                return "redirect:/admin_category_list";
            } else {
                request.setAttribute("message","用户名或密码错误");
                request.getRequestDispatcher("adminLogin").forward(request,response);
            }
        }
        return null;
    }

    //管理员退出登录
    @RequestMapping(value="admin_logout")
    public String loggout(HttpSession session) {
        session.setAttribute("admin",null);
        return "admin/adminLogin";
    }


    @RequestMapping(value="admin_password_update",method = RequestMethod.POST)
    public String updatePassword(String newpassword,Model model,HttpSession session) throws Exception{
        Admin admin = null;
        if( null!=newpassword) {
            admin = (Admin) session.getAttribute("admin");
//            if(password != admin.getPassword()) {
//                model.addAttribute("message","原密码错误");
//            }
            admin.setPassword(newpassword);
            adminService.updatePassword(admin);
            session.setAttribute("admin",null);
        }

        return "admin/adminLogin";
    }

}
