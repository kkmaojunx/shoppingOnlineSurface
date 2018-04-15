package com.example.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * 商品信息
 */
@Entity
@Table(name = "shopping")
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class Shopping implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;         // 主键
    @Column(length = 255)
    private String title;        // 商品名字
    @OneToMany
    @JoinColumn(name = "sid")
    private Set<File> imageurl;      // 图片
    private Integer oldmoney;      // 老旧的价格
    private Integer realmoney;     // 现在的价格
    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;     // 商品描述
    private Integer hot;        // 热度
    private String activity_img;
    private Integer activity;   // 是否活动
    private Integer count;      // 查看次数
    @OneToMany
    @JoinColumn(name = "shopid")
    private Set<ShopLabel> label;    // 商品标签
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchantid; // 商家信息

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<File> getImageurl() {
        return imageurl;
    }

    public void setImageurl(Set<File> imageurl) {
        this.imageurl = imageurl;
    }

    public Integer getOldmoney() {
        return oldmoney;
    }

    public void setOldmoney(Integer oldmoney) {
        this.oldmoney = oldmoney;
    }

    public Integer getRealmoney() {
        return realmoney;
    }

    public void setRealmoney(Integer realmoney) {
        this.realmoney = realmoney;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }

    public Integer getActivity() {
        return activity;
    }

    public void setActivity(Integer activity) {
        this.activity = activity;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<ShopLabel> getLabel() {
        return label;
    }

    public void setLabel(Set<ShopLabel> label) {
        this.label = label;
    }

    public Merchant getMerchantid() {
        return merchantid;
    }

    public void setMerchantid(Merchant merchantid) {
        this.merchantid = merchantid;
    }

    public String getActivity_img() {
        return activity_img;
    }

    public void setActivity_img(String activity_img) {
        this.activity_img = activity_img;
    }
}
