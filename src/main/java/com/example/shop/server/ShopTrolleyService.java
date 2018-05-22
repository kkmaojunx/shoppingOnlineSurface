package com.example.shop.server;

import com.example.shop.entity.ShopTrolley;
import com.example.shop.entity.Shopping;
import com.example.shop.entity.User;

import java.util.List;

/**
 * 购物车Service接口
 */
public interface ShopTrolleyService {

    /**
     * 查询用户的购物车通过用户id
     * @param shopTrolley  用户id
     * @return
     */
    public List<ShopTrolley> ShopTrolleyByUserId(ShopTrolley shopTrolley);

    /**
     * 添加商品到购物车
     * @param shopTrolley   商品信息
     * @return
     */
    public Integer appendShopToShopTrolley(ShopTrolley shopTrolley);

    /**
     * 删除购物车商品通过id
     * @param id    购物车id
     */
    public void removeShopTrolleyById(Integer id);

    /**
     * 已购买数量
     * @param shopTrolley
     * @return
     */
    public Long alreadyBuyTotal(ShopTrolley shopTrolley);

    /**
     * 通过id查询单个实体类
     * @param id
     * @return
     */
    public ShopTrolley findOneShopTrolley(Integer id);

    /**
     *查询商家的已购买商品
     * @return
     */
    public List<ShopTrolley> shopSoldByMerChantId(Integer id);
}
