package com.zjy.dandelion.entity;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * 产品类型
 *
 * @author zhuangjy
 */
@Table(name = "product_sort")
public class ProductSort extends BaseEntity {


    private static final long serialVersionUID = -3903494386748766657L;
    @Column
    private String name;//公司名字

    @Column
    private String note;//备注

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


}
