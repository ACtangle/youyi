package com.how2java.youyi.controller;

import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.how2java.youyi.comparator.*;
import com.how2java.youyi.pojo.*;
import com.how2java.youyi.service.*;
import com.sun.org.apache.regexp.internal.RE;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

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
            productService.setSaleAndReviewNumber(category.getProducts());
        }
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

    //生成订单
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



    //查询订单并返回相关信息
    @RequestMapping(value="fore/showOrder",method=RequestMethod.POST)
    @ResponseBody
    public Object showOrder(HttpServletRequest httpServletRequest) throws Exception {
        String requestString = userController.getRequestString(httpServletRequest);
        JSONObject json = JSONObject.fromObject(requestString);
        Order order = null;
        User user = null;
        OrderItem orderItem = null;
        Product product = null;
        if (json.containsKey("oid")) {
            int oid = json.getInt("oid");
            order = orderService.get(oid);
            order.setStatus(OrderService.waitDelivery);
            order.setPayDate(new Date());
            orderService.update(order);
            if(json.containsKey("userData")){
                Gson gson = new Gson();
                user = gson.fromJson(json.getString("userData"), User.class);
                orderItemService.fill(order);
                //减少库存
                List<OrderItem> orderItems = order.getOrderItems();
                for(int i =0;i<orderItems.size();i++) {
                    orderItem = orderItems.get(i);
                    product = orderItem.getProduct();
                    product.setStock(product.getStock()-orderItem.getNumber());
                }
            }
        }
        return order;
    }


    //获取订单项数据
    @RequestMapping(value="fore/showBought",method=RequestMethod.POST)
    @ResponseBody
    public Object showBought(HttpServletRequest request) throws Exception {
        String requestString = userController.getRequestString(request);
        JSONObject json =  JSONObject.fromObject(requestString);
        User user = null;
//        Boolean flag = false;
        List<Order> orders = new ArrayList<>();
        if(json.containsKey("userData")) {
            Gson gson = new Gson();
            user = gson.fromJson(json.getString("userData"), User.class);
            if (null != user) {
//                flag = true;
                orders = orderService.list(user.getId(),OrderService.delete);
                orderItemService.fill(orders);
            }
        }
        return orders;
    }

    //删除指定订单
    @RequestMapping(value="fore/deleteOrder",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteOrderBy(HttpServletRequest httpServletRequest) throws Exception{
        String requestString = userController.getRequestString(httpServletRequest);
        JSONObject json = JSONObject.fromObject(requestString);
        Boolean flag = false;
        if(json.containsKey("oid")){
            int oid = json.getInt("oid");
//            System.out.println("===============================================" +oid);
            orderService.delete(oid);
            flag = true;
        }
        return flag;
    }

    //购物车删除指定订单项
    @RequestMapping(value="fore/deleteOrderItem",method = RequestMethod.POST)
    @ResponseBody
    public Object deleteOrderItemBy(HttpServletRequest httpServletRequest) throws Exception {
        String requestString  = userController.getRequestString(httpServletRequest);
        JSONObject json = JSONObject.fromObject(requestString);
        boolean flag = false;
        if (json.containsKey("oiid")) {
            int oiid = json.getInt("oiid");
            orderItemService.delete(oiid);
            flag = true;
        }
        return flag;
    }

    //确认收货
    @RequestMapping(value="fore/confirmPayFun",method = RequestMethod.POST)
    @ResponseBody
    public Object confirmPayFunction (HttpServletRequest httpServletRequest) throws Exception {
        String requestString = userController.getRequestString(httpServletRequest);
        JSONObject json = JSONObject.fromObject(requestString);
        Order order = null;
        boolean flag = false;
        Object object = null;
        if (json.containsKey("oid")) {
            int oid  = json.getInt("oid");
            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++" + oid);
            order = orderService.get(oid);
            if (null != order) {
                orderItemService.fill(order);
                flag = true;
            }
        }
        if(flag == true) {
            object = order;
        }
        else{
            object = flag;
        }
        return object;
    }

    //确认付款
    @RequestMapping(value = "fore/orderConfirming",method = RequestMethod.POST)
    @ResponseBody
    public Object orderConfirmed(HttpServletRequest httpServletRequest) throws Exception {
        String requestString = userController.getRequestString(httpServletRequest);
        JSONObject json = JSONObject.fromObject(requestString);
        boolean flag = false;
        Order order = null;
        if(json.containsKey("oid")) {
            int oid = json.getInt("oid");
            order = orderService.get(oid);
            if(order.getStatus().equals(OrderService.waitReview)) {
                flag = false;
            }else {
                order.setStatus(OrderService.waitReview);
                order.setConfirmDate(new Date());
                orderService.update(order);
                if (null != order)
                    flag = true;
            }
        }
        return flag;
    }


    //商品评价展示
    @RequestMapping(value = "fore/showReview",method = RequestMethod.POST)
    @ResponseBody
    public List showReview(HttpServletRequest httpServletRequest) throws Exception {
        String requestString = userController.getRequestString(httpServletRequest);
        JSONObject json = JSONObject.fromObject(requestString);
        List list  = new ArrayList();
        Order order = null;
        if(json.containsKey("oid")) {
            int oid = json.getInt("oid");
            order = orderService.get(oid);
            orderItemService.fill(order);
            Product p = order.getOrderItems().get(0).getProduct();
            List<Review> reviews = reviewService.list(p.getId());
            productService.setSaleAndReviewNumber(p);
            list.add(order);
            list.add(p);
            list.add(reviews);
        }
        return list;
    }

    //用户评论
    @RequestMapping(value = "fore/doReview",method = RequestMethod.POST)
    @ResponseBody
    public Object doReviewByUser(HttpServletRequest httpServletRequest) throws Exception {
        String requestString = userController.getRequestString(httpServletRequest);
        JSONObject json = JSONObject.fromObject(requestString);
        Order order = null;
        Product product = null;
        User user = null;
        String content = "";
        boolean flag = false;
        int pid = 0;
        if(json.containsKey("oid")) {
            int oid = json.getInt("oid");
            order = orderService.get(oid);
            order.setStatus(OrderService.finish);
            orderService.update(order);
        }
        if (json.containsKey("pid")) {
            pid = json.getInt("pid");
            product = productService.get(pid);
        }
        if(json.containsKey("content")) {
            content = HtmlUtils.htmlEscape(json.getString("content"));
        }
        if(json.containsKey("uid")) {
            int uid = json.getInt("uid");
            user = userService.get(uid);
            Review review = new Review();
            review.setContent(content);
            review.setPid(pid);
            review.setCreateDate(new Date());
            review.setUid(user.getId());
            reviewService.add(review);
            flag = true;
        }
        return flag;
    }

    //获取商品评价
    @RequestMapping(value="fore/getReview",method =  RequestMethod.POST)
    @ResponseBody
    public Object getReviews(HttpServletRequest httpServletRequest) throws Exception {
        String requestString = userController.getRequestString(httpServletRequest);
        JSONObject json = JSONObject.fromObject(requestString);
        Product product = null;
        List reviews = new ArrayList();
        if(json.containsKey("pid")) {
            int pid = json.getInt("pid");
            product = productService.get(pid);
            if(null != product) {
                reviews = reviewService.list(product.getId());
                productService.setSaleAndReviewNumber(product);
            }
        }

        return reviews;
    }
}




