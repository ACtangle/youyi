package com.how2java.youyi.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.how2java.youyi.comparator.*;
import com.how2java.youyi.pojo.*;
import com.how2java.youyi.service.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
            return category;
        }





        //分类页面下的产品按条件排序
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




        //搜索功能
        //按关键字模糊查询
    @RequestMapping(value="fore/searchProduct",method = RequestMethod.POST)
    @ResponseBody
    public Object foreSearch(HttpServletRequest request) throws Exception {
        String requestString = userController.getRequestString(request);
        JSONObject json = JSONObject.fromObject(requestString);
        String keyword = "";
        if (json.containsKey("data")){
            keyword = json.getString("data");
        }
//        PageHelper.offsetPage(5,20);
        List<Product> products = productService.search(keyword);
        productService.setSaleAndReviewNumber(products);
        return products;
    }




        //立即购买商品
        @RequestMapping(value="fore/buyone",method=RequestMethod.POST)
        @ResponseBody
        public Object buyone(HttpServletRequest request) throws Exception{
            String requestString = userController.getRequestString(request);
            JSONObject json = JSONObject.fromObject(requestString);
            int pid;int num;
            pid = json.getInt("pid");
            num = json.getInt("num");
            User user = null;Product p = null;
            if(json.containsKey("user")){
                Gson gson = new Gson();
                user = gson.fromJson(json.getString("user"),User.class);
            }
            if(json.containsKey("pid") && json.containsKey("num")) {
                p = productService.get(pid);
            }
            int oiid = 0;
            boolean found = false;
            List<OrderItem> ois = orderItemService.listByUser(user.getId());
            for (OrderItem oi : ois) {
                if(oi.getProduct().getId().intValue() == p.getId().intValue()){
                    oi.setNumber(oi.getNumber()+num);
                    orderItemService.update(oi);
                    found = true;
                    oiid = oi.getId();
                    break;
                }
            }
            if(!found){
                OrderItem oi = new OrderItem();
                oi.setUid(user.getId());
                oi.setNumber(num);
                oi.setPid(pid);
                orderItemService.add(oi);
                oiid = oi.getId();
            }
            return oiid;
        }




    //展示用户所有订单项数据
    @RequestMapping(value="fore/onebuy",method=RequestMethod.POST)
    @ResponseBody
    public Object buy(HttpServletRequest request) throws Exception {
        String requestString = userController.getRequestString(request);
        JSONObject json = JSONObject.fromObject(requestString);
        List<OrderItem> ois = new ArrayList<>();
        List<Object> list = new ArrayList<>();

        int[] oiidsArray = null;
        int oiid;
        float total = 0;

        if(json.containsKey("oiids")){
            JSONArray oiids = json.getJSONArray("oiids");
            oiidsArray = new int[oiids.size()];
            System.out.println(oiids);
            for (int i=0;i<oiids.size();i++) {
                oiidsArray[i] = oiids.getInt(i);
            }

            for (int id : oiidsArray)
            {
                OrderItem oi= orderItemService.get(id);
                total +=oi.getProduct().getPromotePrice()*oi.getNumber();
                ois.add(oi);
            }
        }

        if (json.containsKey("data")) {
            oiid = Integer.parseInt(JSONObject.fromObject(json.getString("data")).getString("id"));
            OrderItem oi = orderItemService.get(oiid);
            total += oi.getProduct().getPromotePrice() * oi.getNumber();
            ois.add(oi);
        }
        list.add(ois);
        list.add(total);
        return list;
    }




    //加入购物车
    @RequestMapping(value="fore/addCart",method=RequestMethod.POST)
    @ResponseBody
    public Object addCart(HttpServletRequest request) throws Exception {
        String requestString = userController.getRequestString(request);
        JSONObject json = JSONObject.fromObject(requestString);
        int pid;int num;
        List list = new ArrayList();
        pid = json.getInt("pid");
        num = json.getInt("num");
        User user = null;Product p = null;
        if(json.containsKey("user")){
            Gson gson = new Gson();
            user = gson.fromJson(json.getString("user"),User.class);
        }
        if(json.containsKey("pid") && json.containsKey("num")) {
            p = productService.get(pid);
        }
        boolean found = false;

        List<OrderItem> ois = orderItemService.listByUser(user.getId());
        for (OrderItem oi : ois) {
            if(oi.getProduct().getId().intValue()==p.getId().intValue()){
                oi.setNumber(oi.getNumber()+num);
                orderItemService.update(oi);
                found = true;
                break;
            }
        }
        if(!found){
            OrderItem oi = new OrderItem();
            oi.setUid(user.getId());
            oi.setNumber(num);
            oi.setPid(pid);
            orderItemService.add(oi);
        }

        list.add("success");
        return list;
    }


    //展示购物车列表
    @RequestMapping(value="fore/showCart",method=RequestMethod.POST)
    @ResponseBody
    public Object cart( HttpServletRequest request ) throws Exception {
        String requestString =userController.getRequestString(request);
        JSONObject json = JSONObject.fromObject(requestString);
        User user = null;
        List<OrderItem> ois = new ArrayList<>();
        if(json.containsKey("user")) {
            Gson gson = new Gson();
            user = gson.fromJson(json.getString("user"),User.class);
            if(null != user) {
                ois = orderItemService.listByUser(user.getId());
            }
        }
        return ois;
    }

    //
    @RequestMapping(value="fore/createOrder",method = RequestMethod.POST)
    @ResponseBody
    public Object createOrder( HttpServletRequest httpServletRequest) throws Exception{
        Order order = new Order();
        String request = userController.getRequestString(httpServletRequest);
        JSONObject json = JSONObject.fromObject(request);
        User user = null;
        float total = 0;
        int[] oiidsArray = null;
        JSONArray oiids;
        List<OrderItem> ois = new ArrayList<OrderItem>();
        List<Object> list = new ArrayList<>();
        JSONObject jsonForUserInfo;
        if (json.containsKey("userInfo")) {
            jsonForUserInfo = (JSONObject)json.get("userInfo");
            order.setAddress(jsonForUserInfo.getString("address"));
            if(jsonForUserInfo.getString("postCode") != null) {
                order.setPost(jsonForUserInfo.getString("postCode"));
            }else{
                order.setPost(null);
            }
            order.setReceiver(jsonForUserInfo.getString("receiver"));
            order.setMobile(jsonForUserInfo.getString("mobile"));
        }
        if(json.containsKey("user")) {
            Gson gson = new Gson();
            user = gson.fromJson(json.getString("user"),User.class);
            if(null != user) {
                String orderCode = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()) + RandomUtils.nextInt(10000);
                order.setOrderCode(orderCode);
                order.setCreateDate(new Date());
                order.setUid(user.getId());
                order.setStatus(OrderService.waitPay);
                if(json.containsKey("oiids")) {
                    oiids = json.getJSONArray("oiids");
                    oiidsArray = new int[oiids.size()];
                    System.out.println(oiids);
                    for (int i=0;i<oiids.size();i++) {
                        oiidsArray[i] = oiids.getInt(i);
                        OrderItem oI =orderItemService.get(oiidsArray[i]);
                        ois.add(oI);
                    }
                    total = orderService.add(order, ois);
                }
            }
        }
        list.add(order);
        list.add(total);
        return list;
    }

}




