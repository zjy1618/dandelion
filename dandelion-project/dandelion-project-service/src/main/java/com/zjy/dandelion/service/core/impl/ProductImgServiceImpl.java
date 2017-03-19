package com.zjy.dandelion.service.core.impl;

import com.zjy.dandelion.dao.mapper.ProductImgMapper;
import com.zjy.dandelion.entity.ProductImg;
import com.zjy.dandelion.service.core.ProductImgService;
import com.zjy.dandelion.utils.ImageTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhuangjy on 16/8/15.
 */
@Service
public class ProductImgServiceImpl implements ProductImgService {

    @Resource
    private ProductImgMapper productImgMapper;

    @Override
    public List<ProductImg> findByProductCode(String productCode) {
        List<ProductImg> list = productImgMapper.findByProductCode(productCode);
        if(list == null || list.size() == 0){
            list = new ArrayList<>();
            ProductImg productImg = new ProductImg();
            productImg.setUrl("/product_img/default.jpg");
            list.add(productImg);
        }
        return list;
    }

    @Override
    public boolean saveProductImg(ProductImg productImg) {
        productImg.setCreateDate(new Date());
        productImg.setModifyDate(new Date());
        return productImgMapper.insert(productImg) > 0;
    }

    @Override
    public boolean updateProductImg(ProductImg productImg) {
        ProductImg productImg_org = productImgMapper.findSmallByProductCode(productImg.getProductCode());
        boolean b;
        if(productImg_org != null){
            productImg_org.setModifyDate(new Date());
            if(StringUtils.isNotBlank(productImg.getUrl())){
                productImg_org.setUrl(productImg.getUrl());
            }
            if(productImg.getType() != null){
                productImg_org.setType(productImg.getType());
            }
            b = productImgMapper.updateByPrimaryKey(productImg_org) > 0;
        } else {
            b = this.saveProductImg(productImg)  ;
        }
        return b;
    }

    @Override
    public ProductImg findProductImgById(Long id) {
        return productImgMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProductImg findSmallByProductCode(String productCode) {
        ProductImg productImg = productImgMapper.findSmallByProductCode(productCode);
        return productImg;
    }


    @Override
    public int deleteByProductCode(String productCode, String realPath) {
        List<ProductImg> list = this.findByProductCode(productCode);
        for(ProductImg productImg:list){
            if(StringUtils.isNotBlank(productImg.getUrl())){
                String fileName = productImg.getUrl().substring(productImg.getUrl().lastIndexOf("/"));
                ImageTool.deletePicture(realPath + File.separator + fileName);
            }
        }
        return productImgMapper.deleteByProductCode(productCode);
    }
}
