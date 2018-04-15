package com.example.shop.entity;


import javax.persistence.*;

@Entity
@Table(name = "shoptrolley")
public class ShopTrolley {

    @Id
    @GeneratedValue
    private Integer id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User userid;
    @Column(length = 255)
    private String shoptitle;
    private Integer shopcount;
    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shopping shoppingid;

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
}
