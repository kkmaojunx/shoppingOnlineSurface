package com.example.shop.server;

import com.example.shop.entity.ShopLabel;

/**
 * 标签service
 */
public interface ShopLabelService {

    /**
     * 查询商品标签通过商品id
     * @param id
     * @return
     */
    public ShopLabel findShopLabelById(Integer id);

    /**
     * 保存或者修改标签
     * @param shopLabel
     */
    public void saveOrUpdateShopLabel(ShopLabel shopLabel);

    /**
     * 删除商品的标签通过标签id
     * @param id 标签id
     */
    public void deleteShopLabelById(Integer id);
}
