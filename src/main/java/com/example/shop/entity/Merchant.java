package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 卖家信息
 */
@Entity
@Table(name = "merchant")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Merchant implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;         // 主键
    @Column(length = 255)
    private String name;        // 名字
    @Column(length = 255)
    private String imageHead;   // 头像
    @Column(length = 255)
    private String ipAddress;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;     // 描述
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userId;        // 用户id

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageHead() {
        return imageHead;
    }

    public void setImageHead(String imageHead) {
        this.imageHead = imageHead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageHead='" + imageHead + '\'' +
                ", ipAddress='" + ipAddress + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }
}
