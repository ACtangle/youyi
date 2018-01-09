package com.how2java.youyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.pojo.Product;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.service.ProductService;
import com.how2java.youyi.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by melon on 18-1-8.
 */
@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_product_list")
    public String list(Model model,int cid,Page page) {
        Category c = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Product> ps = productService.list(cid);
        int total = (int) new PageInfo<>(ps).getTotal();
        page.setTotal(total);
        page.setParam("&cid="+c.getId());
        model.addAttribute("c",c);
        model.addAttribute("ps",ps);
        model.addAttribute("page",page);
        return "admin/listProduct";
    }

    @RequestMapping(value="admin_product_add")
    public String add(Model model,Product product) {
        productService.add(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }

    @RequestMapping(value="admin_product_delete")
    public String delete(Model model,int id) {
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:admin_product_list?cid="+product.getCid();
    }

    @RequestMapping("admin_product_edit")
    public String edit(Model model,int id) {
        Product product = productService.get(id);
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        model.addAttribute("p",product);
        return "admin/editProduct";
    }

    @RequestMapping("admin_product_update")
    public String update(Model model,Product product) {
        productService.update(product);
        return "redirect:admin_product_list?cid="+product.getCid();
    }
}
