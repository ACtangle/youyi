package com.how2java.youyi.controller;

import com.how2java.youyi.pojo.Category;
import com.how2java.youyi.pojo.Product;
import com.how2java.youyi.pojo.ProductImage;
import com.how2java.youyi.service.CategoryService;
import com.how2java.youyi.service.ProductImageService;
import com.how2java.youyi.service.ProductService;
import com.how2java.youyi.util.ImageUtil;
import com.how2java.youyi.util.UploadImageFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by melon on 18-1-8.
 */
@Controller
@RequestMapping("")
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("admin_productImage_list")
    public String list(Model model,int pid) {
        Product product = productService.get(pid);
        Category category = categoryService.get(product.getCid());
        product.setCategory(category);
        List<ProductImage> pisSingle = productImageService.list(pid,ProductImageService.type_single);
        List<ProductImage> pisDetail = productImageService.list(pid,ProductImageService.type_detail);
        model.addAttribute("p",product);
        model.addAttribute("pisSingle",pisSingle);
        model.addAttribute("pisDetail",pisDetail);
        return "admin/listProductImage";
    }

    @RequestMapping("admin_productImage_add")
    public String add(ProductImage productImage, HttpSession session, UploadImageFile uploadImageFile) {
        productImageService.add(productImage);
        String fileName = productImage.getId()+".jpg";
        String imgFolder;
        String imgFolder_small = null;
        String imgFolder_middle = null;
        if(ProductImageService.type_single.equals(productImage.getType())) {
            imgFolder = session.getServletContext().getRealPath("img/productSingle");
            imgFolder_small = session.getServletContext().getRealPath("img/productSingle_small");
            imgFolder_middle = session.getServletContext().getRealPath("img/productSingle_middle");
        }else{
            imgFolder = session.getServletContext().getRealPath("img/productDetail");
        }
        File file = new File(imgFolder,fileName);
        file.getParentFile().mkdirs();
        try{
            uploadImageFile.getImage().transferTo(file);
            BufferedImage img = ImageUtil.change2jpg(file);
            ImageIO.write(img,"jpg",file);
            if (ProductImageService.type_single.equals(productImage.getType())) {
                File f_small = new File(imgFolder_small,fileName);
                File f_middle = new File(imgFolder_middle,fileName);
                ImageUtil.resizeImage(file,56,56,f_small);
                ImageUtil.resizeImage(file,217,190,f_middle);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:admin_productImage_list?pid="+productImage.getPid();
    }

    @RequestMapping("admin_productImage_delete")
    public String delete(int id,HttpSession session) {
        ProductImage pi = productImageService.get(id);

        String fileName = pi.getId()+ ".jpg";
        String imageFolder;
        String imageFolder_small=null;
        String imageFolder_middle=null;

        if(ProductImageService.type_single.equals(pi.getType())){
            imageFolder= session.getServletContext().getRealPath("img/productSingle");
            imageFolder_small= session.getServletContext().getRealPath("img/productSingle_small");
            imageFolder_middle= session.getServletContext().getRealPath("img/productSingle_middle");
            File imageFile = new File(imageFolder,fileName);
            File f_small = new File(imageFolder_small,fileName);
            File f_middle = new File(imageFolder_middle,fileName);
            imageFile.delete();
            f_small.delete();
            f_middle.delete();

        }
        else{
            imageFolder= session.getServletContext().getRealPath("img/productDetail");
            File imageFile = new File(imageFolder,fileName);
            imageFile.delete();
        }

        productImageService.delete(id);

        return "redirect:admin_productImage_list?pid="+pi.getPid();
    }
}
