package com.example.shop.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "activity")
public class Activity {

    @Id
    @GeneratedValue
    private Integer id;         // 主键
    @Column(length = 255)
    private String name;        // 活动名字
    @Column(length = 255)
    private String imageUrl;    // 活动配图
    private Integer shopid;
    @OneToMany
    @JoinColumn(name = "sid")
    private Set<File> fileid;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getShopid() {
        return shopid;
    }

    public void setShopid(Integer shopid) {
        this.shopid = shopid;
    }

    public Set<File> getFileid() {
        return fileid;
    }

    public void setFileid(Set<File> fileid) {
        this.fileid = fileid;
    }
}
