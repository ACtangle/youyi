package com.how2java.youyi.service;

import com.how2java.youyi.pojo.Order;
import com.how2java.youyi.pojo.OrderItem;

import java.util.List;

/**
 * Created by melon on 18-1-10.
 */
public interface OrderService {
    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm = "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order c);
    float add(Order c,List<OrderItem> ois);
    void delete(int id);
    void update(Order c);
    Order get(int id);
    List list();


}
