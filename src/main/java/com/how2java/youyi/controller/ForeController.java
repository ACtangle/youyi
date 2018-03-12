package com.how2java.youyi.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.how2java.youyi.comparator.*;
import com.how2java.youyi.pojo.*;
import com.how2java.youyi.service.*;
import net.sf.json.JSON;
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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by melon on 18-1-16.
 */

@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    UserController userController;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    UserService userService;
    @Autowired
    ProductImageService productImageService;
    @Autowired
    PropertyValueService propertyValueService;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderItemService orderItemService;

    //分类及其分类下产品数据源
    @RequestMapping(value="fore/showCategorys",method=RequestMethod.GET)
    @ResponseBody
    public Object categoryAndCoursel() {
        List<Category> categories = categoryService.list();
        productService.fill(categories);
        productService.fillByRow(categories);
        return categories;
    }

    //单个产品相关信息
    @RequestMapping(value="fore/showProduct",method=RequestMethod.POST)
    @ResponseBody
    public Object showProductaa(HttpServletRequest request) throws Exception{
        String requestString = userController.getRequestString(request);
        int pid;
        Product p = null;
        List<Object> list = new ArrayList<>();
        JSONObject json = JSONObject.fromObject(requestString);
            if(json.containsKey("data")){
                pid = Integer.parseInt(JSONObject.fromObject(json.get("data").toString()).get("id").toString());
                System.out.println(pid);
                p = productService.get(pid);
            }
            if (p != null){
                List<ProductImage> productSingleImages = productImageService.list(p.getId(), ProductImageService.type_single);
                List<ProductImage> productDetailImages = productImageService.list(p.getId(), ProductImageService.type_detail);
                p.setProductSingleImages(productSingleImages);
                p.setProductDetailImages(productDetailImages);
                List<PropertyValue> pvs = propertyValueService.list(p.getId());
                List<Review> reviews = reviewService.list(p.getId());
                productService.setSaleAndReviewNumber(p);
                list.add(p);
                list.add(pvs);
                list.add(reviews);
            }
        return list;
    }

    //分类产品页面
    @RequestMapping(value = "fore/showCategoryProducts",method = RequestMethod.POST)
    @ResponseBody
    public Object categoryProducts(HttpServletRequest request) throws Exception{
        String requestString = userController.getRequestString(request);
        JSONObject json = JSONObject.fromObject(requestString);
        Category tempcategory = null;
        Category category = null;
        String sort = "";
        if(json.containsKey("data")){
            Gson gson = new Gson();
            tempcategory = gson.fromJson(json.getString("data"),Category.class);
            if(tempcategory != null){
                category = categoryService.get(tempcategory.getId());
            }
        }
        if(json.containsKey("sort")) {
            sort = json.getString("sort");
        }

        if(category != null) {
            productService.fill(category);
            productService.setSaleAndReviewNumber(category.getProducts());}
//        if(null!=sort){
//            System.out.println("================================================="+sort);
//            switch(sort){
//                case "review":
//                    Collections.sort(category.getProducts(),new ProductReviewComparator());
//                    break;
//                case "date" :
//                    Collections.sort(category.getProducts(),new ProductDateComparator());
//                    break;
//
//                case "saleCount" :
//                    Collections.sort(category.getProducts(),new ProductSaleCountComparator());
//                    break;
//
//                case "price":
//                    Collections.sort(category.getProducts(),new ProductPriceComparator());
//                    break;
//
//                case "all":
//                    Collections.sort(category.getProducts(),new ProductAllComparator());
//                    break;
//            }
//        }
            return category;
        }

        @RequestMapping(value="fore/showProductsSort",method = RequestMethod.POST)
        @ResponseBody
        public Object showProductsSort(HttpServletRequest request) throws  Exception{
            String requestString = userController.getRequestString(request);
            List<Product> list = new ArrayList<Product>();
            JSONObject json = JSONObject.fromObject(requestString);
            Category tempcategory = null;
            Category category = null;
            String sort = "";
            if(json.containsKey("data")){
                Gson gson = new Gson();
                String a = "";
                tempcategory = gson.fromJson(json.getString("data"),Category.class);
                if(tempcategory != null){
                    category = categoryService.get(tempcategory.getId());
                }
            }
            if(json.containsKey("sort")) {
                String sortdata = json.getString("sort");
                JSONObject jsonObject = JSONObject.fromObject(sortdata);
                sort = jsonObject.getString("sort");
            }

            if(category != null) {
                productService.fill(category);
                productService.setSaleAndReviewNumber(category.getProducts());}
        if(null != sort){
            switch(sort){
                case "review":
                    Collections.sort(category.getProducts(),new ProductReviewComparator());
                    break;
                case "date" :
                    Collections.sort(category.getProducts(),new ProductDateComparator());
                    break;

                case "saleCount" :
                    Collections.sort(category.getProducts(),new ProductSaleCountComparator());
                    break;

                case "price":
                    Collections.sort(category.getProducts(),new ProductPriceComparator());
                    break;

                case "all":
                    Collections.sort(category.getProducts(),new ProductAllComparator());
                    break;
            }
        }
            return category;
        }
}




