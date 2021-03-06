package com.how2java.youyi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.util.ImageUtil;
import com.how2java.youyi.util.Page;
import com.how2java.youyi.util.UploadImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(),page.getCount());
        List<Category> cs = categoryService.list();
//        int total = categoryService.total();
//        page.setTotal(total);
        int total = (int) new PageInfo<>(cs).getTotal();
        page.setTotal(total);
        model.addAttribute("cs",cs);
        model.addAttribute("page",page);
        return "admin/listCategory";
    }

    @RequestMapping("admin_category_add")
    public String add(Category c, HttpSession session, UploadImageFile uploadImageFile) throws IOException {
        categoryService.add(c);
//        System.out.println(c.getId());
        File  imageFolder= new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,c.getId()+".jpg");
        if(!file.getParentFile().exists())
            file.getParentFile().mkdirs();
//        System.out.println(uploadImageFile);
//        System.out.println(uploadImageFile.getImage());
//        System.out.println(file);
        uploadImageFile.getImage().transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        return "redirect:/admin_category_list";
    }

    @RequestMapping("admin_category_delete")
    public String delete(int id, HttpSession session) throws  IOException {
        categoryService.delete(id);
        File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
        File file = new File(imageFolder,id+".jpg");
        file.delete();
        return "redirect:admin_category_list";
    }

    @RequestMapping("admin_category_edit")
    public String edit(int id,Model model) {
        Category c = categoryService.get(id);
        model.addAttribute("c",c);
        return "admin/editCategory";
    }

    @RequestMapping("admin_category_update")
    public String update(Category category,HttpSession session,UploadImageFile uploadImageFile) throws IOException{
        categoryService.update(category);
        MultipartFile image = uploadImageFile.getImage();
        if (null!=image && !image.isEmpty()) {
            File imageFolder = new File(session.getServletContext().getRealPath("img/category"));
            File file = new File(imageFolder,category.getId() + ".jpg");
            image.transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img,"jpg",file);
        }
        return "redirect:admin_category_list";
    }
}
