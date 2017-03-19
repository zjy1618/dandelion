package com.zjy.dandelion.entity;

import javax.persistence.Column;

/**
 * 后台成员
 * Created by zhuangjy on 16/8/29.
 */
public class Member extends BaseEntity {

    private static final long serialVersionUID = 6254821613932883139L;

    public enum Status{ //账户状态(OPEN 可登录, CLOSE 不可登录提示该用户不存在)
        OPEN, CLOSE,
    }
    @Column
    private String name; // '管理员名称'

    @Column
    private String chineseName; // 管理员中文名称

    @Column
    private String password;

    @Column
    private Status status; //该用户账户的状态(逻辑关闭)

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
