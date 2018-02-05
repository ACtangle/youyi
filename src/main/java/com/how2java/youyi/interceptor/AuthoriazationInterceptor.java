package com.how2java.youyi.interceptor;

import com.how2java.youyi.pojo.Admin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by melon on 18-1-11.
 */
public class AuthoriazationInterceptor implements HandlerInterceptor {

    //不拦截的url请求
    private static final String[] IGNORE_URI = {"/adminLogin","/admin_login","forehome","getUsers"};

    //处理拦截器的方法
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        boolean flag = false;
        String servletpath = httpServletRequest.getServletPath();
        for (String s : IGNORE_URI) {
            if (servletpath.contains(s)) {
                flag = true;
                break;
            }
        }
        //拦截请求
        if(!flag) {
            //获取session的用户
            Admin admin = (Admin) httpServletRequest.getSession().getAttribute("admin");
            //判断用户是否已经登录
            if (admin == null) {
                httpServletRequest.setAttribute("message","请先登录再访问网站");
                httpServletRequest.getRequestDispatcher("adminLogin").forward(httpServletRequest,httpServletResponse);
            }else {
                flag = true;
            }
        }
        return flag;
    }

    //该方法将在controller的方法调用之后执行,方法中可以对ModelAndView进行操作
    //该方法也只能在当前Interceptor的proHandle的返回值为true时才执行
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    //该方法在整个请求完成之后执行,主要用于清理资源
    //该方法也只能在当前Interceptor的proHandle方法的返回值为true时才执行
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
