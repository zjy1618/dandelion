package com.zjy.dandelion.service.core.impl;

import com.zjy.dandelion.dao.mapper.ProductStyleMapper;
import com.zjy.dandelion.entity.ProductStyle;
import com.zjy.dandelion.service.core.ProductStyleService;
import com.zjy.dandelion.utils.ImageTool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Created by zhuangjy on 16/8/15.
 */
@Service
public class ProductStyleServiceImpl implements ProductStyleService {

    @Resource
    private ProductStyleMapper productStyleMapper;

    @Override
    public List<ProductStyle> findByProductCode(String productCode) {
        return productStyleMapper.findByProductCode(productCode);
    }

    @Override
    public boolean saveProductStyle(ProductStyle productStyle) {
        productStyle.setCreateDate(new Date());
        productStyle.setModifyDate(new Date());
        return productStyleMapper.insert(productStyle) > 0;
    }

    @Override
    public boolean updateProductStyle(ProductStyle productStyle) {
        ProductStyle productStyle_org = productStyleMapper.selectByPrimaryKey(productStyle.getId());
        BeanUtils.copyProperties(productStyle,productStyle_org);
        productStyle_org.setModifyDate(new Date());
        return productStyleMapper.updateByPrimaryKey(productStyle_org) > 0;
    }

    @Override
    public ProductStyle findProductStyleById(Long id) {
        return productStyleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int deleteByProductCode(String productCode) {
        return productStyleMapper.deleteByProductCode(productCode);
    }

    @Override
    public boolean deleteById(Long id) {
        return productStyleMapper.deleteById(id) > 0;
    }
}
