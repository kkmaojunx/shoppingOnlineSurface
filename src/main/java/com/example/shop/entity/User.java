package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;         // 主键
    @Column(length = 255)
    private String username;    // 用户名
    @Column(length = 255)
    private String password;    // 密码
    @Past                       // 必须为一个过去的时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;      // 生日
    @Column(length = 255)
    private String content;     // 个人介绍
    @Column(length = 255)
    private String ipAddress;   // ip地址
    @Column(length = 255)
    private String imageHead;   // 头像
    @Column(length = 255)
    private String imageBackground; // 背景图片
    private Integer userStatus; // 用户身份  1为商家   2为买家

    @Transient
    private Integer alreadyBuy; // 已经购买数量
    @Transient
    private Integer shopBus;    // 购物车数量
    @Transient
    private Integer objectFlowIndent;   // 物流订单

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageHead() {
        return imageHead;
    }

    public void setImageHead(String imageHead) {
        this.imageHead = imageHead;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public String getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }

    public Integer getAlreadyBuy() {
        return alreadyBuy;
    }

    public void setAlreadyBuy(Integer alreadyBuy) {
        this.alreadyBuy = alreadyBuy;
    }

    public Integer getShopBus() {
        return shopBus;
    }

    public void setShopBus(Integer shopBus) {
        this.shopBus = shopBus;
    }

    public Integer getObjectFlowIndent() {
        return objectFlowIndent;
    }

    public void setObjectFlowIndent(Integer objectFlowIndent) {
        this.objectFlowIndent = objectFlowIndent;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
