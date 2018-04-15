package com.example.shop.entity;

import javax.persistence.*;

/**
 * 商品标签
 */
@Entity
@Table(name = "shoplabel")
public class ShopLabel {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(length = 255)
    private String name;
    private Integer shopid;


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

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }
}
