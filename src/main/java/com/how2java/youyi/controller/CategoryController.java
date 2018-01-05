package com.how2java.youyi.controller;

import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by melon on 18-1-5.
 */
@Controller
@RequestMapping("")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_category_list")
    public String list(Model model) {
        List<Category> cs = categoryService.list();
        model.addAttribute("cs",cs);
        return "admin/listCategory";
    }
}
