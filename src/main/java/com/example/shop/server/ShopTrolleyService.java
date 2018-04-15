package com.example.shop.server;

import com.example.shop.entity.ShopTrolley;
import com.example.shop.entity.User;

import java.util.List;

/**
 * 购物车Service接口
 */
public interface ShopTrolleyService {

    /**
     * 查询用户的购物车通过用户id
     * @param user  用户id
     * @return
     */
    public List<ShopTrolley> ShopTrolleyByUserId(User user);

    /**
     * 添加商品到购物车
     * @param shopTrolley   商品信息
     * @return
     */
    public Integer appendShopToShopTrolley(ShopTrolley shopTrolley);
}
