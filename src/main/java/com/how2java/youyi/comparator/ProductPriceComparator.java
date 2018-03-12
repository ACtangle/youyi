package com.how2java.youyi.comparator;

import com.how2java.youyi.pojo.Product;

import java.util.Comparator;

/**
 * Created by melon on 18-3-10.
 */
public class ProductPriceComparator implements Comparator<Product> {
    @Override
    public int compare(Product p1, Product p2) {
        int flag = (int)(p1.getPromotePrice()-p2.getPromotePrice());
        int result = 0;
        if(flag == 0)
            result = 0;
        else if(flag > 0){
            result = -1;
        }else if (flag <0)
            result = 1;
        return result;
    }
}
