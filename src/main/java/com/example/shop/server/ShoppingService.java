package com.example.shop.server;

import com.example.shop.entity.Shopping;

import java.util.List;

/**
 * 商品service接口
 */
public interface ShoppingService {

    /**
     * 查询单个商品通过id
     * @param id    查询商品的id
     * @return
     */
    public Shopping ShoppingById(Integer id);

    /**
     * 查询热销商品通过字段hot
     * @param shopping  hot Integer 1 or 0
     * @return
     */
    public List<Shopping> activityShoppingList(Shopping shopping);

    /**
     * 搜索框模糊搜索商品通过商品名字
     * @param shopping  title   String
     * @return
     */
    public List<Shopping> searchShoppingList(Shopping shopping);
}
