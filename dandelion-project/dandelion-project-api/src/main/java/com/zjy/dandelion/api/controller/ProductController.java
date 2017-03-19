package com.zjy.dandelion.api.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.zjy.dandelion.api.jsonp.ProductJsonp;
import com.zjy.dandelion.entity.Product;
import com.zjy.dandelion.entity.ProductSort;
import com.zjy.dandelion.page.Pager;
import com.zjy.dandelion.service.core.ProductService;
import com.zjy.dandelion.service.core.ProductSortService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

@Controller
@RequestMapping("product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @Resource
    private ProductService productService;

    @Resource
    private ProductSortService productSortService;

    @RequestMapping(value = "/list.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONPObject list(int pageNumber, int pageSize, String productName, String productSortId, String callback) {

        ProductJsonp productJsonp = new ProductJsonp();

        Pager<Product> pager = new Pager<>();
        pager.setPageNumber(pageNumber);
        pager.setPageSize(pageSize);
        Map<String, Object> param_map = new HashMap<>();
        param_map.put("status",Product.Status.ONSELL);
        if(StringUtils.isNotBlank(productName)){
            param_map.put("name",productName);
        }
        if(StringUtils.isNotBlank(productSortId)){
            ProductSort productSort = productSortService.findProductSortById(Long.parseLong(productSortId));
            if(productSort != null){
                param_map.put("productSortId", productSort.getId());
                productJsonp.setProductSort(productSort);
            }
        }

        productService.findByPager(param_map, pager);

        productJsonp.setPager(pager);

        return new JSONPObject(callback,productJsonp);
    }


}
