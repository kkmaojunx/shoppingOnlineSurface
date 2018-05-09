package com.example.shop.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "shoptrolley")
@DynamicUpdate
@JsonIgnoreProperties(value={"hibernateLazyInitializer","handler","fieldHandler"})
public class ShopTrolley implements Serializable {

    @Id
    @GeneratedValue
    private Integer id;                                                 // 主键
    @OneToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userid;                                                // 用户id
    @Column(length = 255)
    private String shoptitle;                                           // 商品标题
    private Integer shopcount;                                          // 商品数量
    @ManyToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shopping shoppingid;                                        // 商品详情
    @ManyToOne
    @JoinColumn(name = "label_id")
    private ShopLabel shoplabelid;                                        // 标签
    private Integer buy = 0;                                                // 是否购买 0:在购物车 1：已购买 2：退回 3：已发货 4：待发货
    @Transient
    private String trolleyImg;                                          // 商品图片
    @Transient
    private Integer money;                                              // 价格
    @Transient
    private String lableName;                                           // 标签名字
    @Transient
    private Integer hot;                                                // 商品热度

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserid() {
        return userid;
    }

    public void setUserid(User userid) {
        this.userid = userid;
    }

    public String getShoptitle() {
        return shoptitle;
    }

    public void setShoptitle(String shoptitle) {
        this.shoptitle = shoptitle;
    }

    public Integer getShopcount() {
        return shopcount;
    }

    public void setShopcount(Integer shopcount) {
        this.shopcount = shopcount;
    }

    public Shopping getShoppingid() {
        return shoppingid;
    }

    public void setShoppingid(Shopping shoppingid) {
        this.shoppingid = shoppingid;
    }

    public ShopLabel getShoplabelid() {
        return shoplabelid;
    }

    public void setShoplabelid(ShopLabel shoplabelid) {
        this.shoplabelid = shoplabelid;
    }

    public Integer getBuy() {
        return buy;
    }

    public void setBuy(Integer buy) {
        this.buy = buy;
    }

    public String getTrolleyImg() {
        return trolleyImg;
    }

    public void setTrolleyImg(String trolleyImg) {
        this.trolleyImg = trolleyImg;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public String getLableName() {
        return lableName;
    }

    public void setLableName(String lableName) {
        this.lableName = lableName;
    }

    public Integer getHot() {
        return hot;
    }

    public void setHot(Integer hot) {
        this.hot = hot;
    }
}
