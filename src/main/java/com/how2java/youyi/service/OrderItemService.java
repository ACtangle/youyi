package com.how2java.youyi.service;

import com.how2java.youyi.pojo.Order;
import com.how2java.youyi.pojo.OrderItem;

import java.util.List;

/**
 * Created by melon on 18-1-10.
 */
public interface OrderItemService {
    void add(OrderItem c);

    void delete(int id);
    void update(OrderItem c);
    OrderItem get(int id);
    List list();

    void fill(List<Order> os);

    void fill(Order o);

    int getSaleCount(int  pid);

    List<OrderItem> listByUser(int uid);
}
