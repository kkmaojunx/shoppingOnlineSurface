package com.example.shop.server;

import com.example.shop.entity.Merchant;

/**
 * 店铺service接口
 */
public interface MerchantService {

    /**
     * 查询店铺通过用户id 或者店铺id
     * @param merchant    用户的id 或者店铺id
     * @return
     */
    public Merchant findOneMerchantByUserId(Merchant merchant);

    /**
     * 删除店铺通过店铺id
     * @param id    店铺的id
     */
    public void deleteMerchantById(Integer id);

    /**
     * 添加或者修改店铺
     * @param merchant  店铺信息
     */
    public void addMerchant(Merchant merchant);
}
