package com.how2java.youyi.comparator;

import com.how2java.youyi.pojo.Product;

import java.util.Comparator;

/**
 * Created by melon on 18-3-10.
 */
public class ProductDateComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        return p1.getCreateDate().compareTo(p2.getCreateDate());
    }
}
