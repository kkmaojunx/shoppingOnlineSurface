package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Table(name = "user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;         // 主键
    @Column(length = 255)
    @NotEmpty(message = "用户名不为空")
    private String username;                                    // 用户名
    @Column(length = 255)
    @NotEmpty(message = "密码不为空")
    private String password;    // 密码
    @Past(message = "必须为一个过去的时间")                       // 必须为一个过去的时间
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;                                       // 生日
    @Column(length = 11)
    private Long phone;         // 电话号码
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
    @Transient
    private String headLocal;           // 头像临时路径
    @Transient
    private String backgroundLocal;     // 背景图片临时路径

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
        //这里由于有夏令营时间存在 这是要默认设置时区，@see http://www.cnblogs.com/memory4young/p/java-timezone.html
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getImageHead() {
        return imageHead;
    }

    public void setImageHead(String imageHead) {
        this.imageHead = imageHead;
    }

    public String getImageBackground() {
        return imageBackground;
    }

    public void setImageBackground(String imageBackground) {
        this.imageBackground = imageBackground;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
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

    public String getHeadLocal() {
        return headLocal;
    }

    public void setHeadLocal(String headLocal) {
        this.headLocal = headLocal;
    }

    public String getBackgroundLocal() {
        return backgroundLocal;
    }

    public void setBackgroundLocal(String backgroundLocal) {
        this.backgroundLocal = backgroundLocal;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", phone=" + phone +
                ", content='" + content + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", imageHead='" + imageHead + '\'' +
                ", imageBackground='" + imageBackground + '\'' +
                ", userStatus=" + userStatus +
                ", alreadyBuy=" + alreadyBuy +
                ", shopBus=" + shopBus +
                ", objectFlowIndent=" + objectFlowIndent +
                ", headLocal='" + headLocal + '\'' +
                ", backgroundLocal='" + backgroundLocal + '\'' +
                '}';
    }
}
