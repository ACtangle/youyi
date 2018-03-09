package com.how2java.youyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.how2java.youyi.pojo.User;
import com.how2java.youyi.service.UserService;
import com.how2java.youyi.util.Page;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by melon on 18-1-10.
 */
@Controller
@RequestMapping("")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("admin_user_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<User> us = userService.list();
        int total = (int) new PageInfo<>(us).getTotal();
        page.setTotal(total);
        model.addAttribute("us",us);
        model.addAttribute("page",page);
        return "admin/listUser";
    }

    @RequestMapping(value="fore/getUsers",method=RequestMethod.GET)
    @ResponseBody
    public Object getUsers() {
        List<User> users = userService.list();
        return users;
    }

    //用户注册
    @RequestMapping(value="fore/addUser",method = RequestMethod.POST)
    @ResponseBody
    public Object addUser(HttpServletRequest request) throws Exception{
        String requestStr = getRequestString(request);
        JSONObject json  = JSONObject.fromObject(requestStr);
        boolean isok = false;
        boolean exist = false;
        User user = null;
        if (json.containsKey("data")) {
            Gson gson = new Gson();
            user = gson.fromJson(json.getString("data"),User.class);
            exist = userService.isExist(user.getName());
        }

        if (user != null && !exist) {
            userService.add(user);
            isok = true;
        }
        return isok;
    }

    //用户登录
    @RequestMapping(value="fore/loginUser",method=RequestMethod.POST)
    @ResponseBody
    public Object userLogin(HttpServletRequest request) throws Exception{
        String requestString = getRequestString(request);
        System.out.println(requestString);
        User user = null;
        List<Object> list = new ArrayList<>();
        JSONObject json = JSONObject.fromObject(requestString);
//            System.out.println(json);
        if(json.containsKey("data")) {
            Gson gson = new Gson();
            user = gson.fromJson(json.getString("data"),User.class);
        }
        boolean response = false;
        if (user != null) {
            User sqlUser = userService.get(user.getName(),user.getPassword());
            if(sqlUser.getName().equals(user.getName()) && sqlUser.getPassword().equals(user.getPassword())) {
                response = true;
                user.setId(sqlUser.getId());
            }
        }
        list.add(response);
        list.add(user);
        return list;
    }

    //请求信息转换
    public String getRequestString(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");
        BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        String requestString = sb.toString();
        if (requestString == null) {
            requestString = request.getQueryString();
            requestString = java.net.URLDecoder.decode(requestString,"UTF-8");
        }
        return requestString;
    }
}
