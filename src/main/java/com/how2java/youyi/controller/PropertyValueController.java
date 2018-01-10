package com.how2java.youyi.controller;

import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.pojo.Product;
import com.how2java.youyi.pojo.PropertyValue;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.service.ProductService;
import com.how2java.youyi.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by melon on 18-1-9.
 */
@Controller
@RequestMapping("")
public class PropertyValueController {

    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;


    @RequestMapping(value="admin_propertyValue_edit")
    public String edit(Model model,int pid) {
        Product product = productService.get(pid);
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        propertyValueService.init(product);
        List<PropertyValue> pvs = propertyValueService.list(pid);
        model.addAttribute("p",product);
        model.addAttribute("pvs",pvs);
        return "admin/editPropertyValue";
    }

    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue) {
        propertyValueService.update(propertyValue);
        return "success";
    }
}
