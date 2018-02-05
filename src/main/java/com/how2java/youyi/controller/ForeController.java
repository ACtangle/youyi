package com.how2java.youyi.controller;

import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by melon on 18-1-16.
 */

@Controller
@RequestMapping(value="")
public class ForeController {

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;

//    @RequestMapping("forehome")
//    public String home(Model model) {
//        List<Category> categories = categoryService.list();
//        productService.fill(categories);
//        productService.fillByRow(categories);
//        model.addAttribute("cs",categories);
//        return "fore/home";
//    }
}
