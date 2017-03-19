package com.zjy.dandelion.api.jsonp;

import com.zjy.dandelion.entity.Product;
import com.zjy.dandelion.entity.ProductSort;
import com.zjy.dandelion.page.Pager;

import java.io.Serializable;

/**
 * 产品列表返回Jsonp对象
 * Created by zhuangjy on 16/8/28.
 */
public class ProductJsonp implements Serializable {

    private static final long serialVersionUID = 6573065273045984716L;

    private Pager<Product> pager;

    private ProductSort productSort;

    public Pager<Product> getPager() {
        return pager;
    }

    public void setPager(Pager<Product> pager) {
        this.pager = pager;
    }


    public ProductSort getProductSort() {
        return productSort;
    }

    public void setProductSort(ProductSort productSort) {
        this.productSort = productSort;
    }
}
