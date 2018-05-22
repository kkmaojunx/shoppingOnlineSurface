package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 活动实体类
 */
@Entity
@Table(name = "activity")
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class Activity implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;         // 主键
    @Column(length = 255)
    private String name;        // 活动名字
    @Column(length = 255)
    private String imageUrl;    // 活动配图
    private Integer shopid;
    @OneToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "sid")
    private Set<File> fileid;
    @Column(length = 255)
    private String ipAddress;

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
