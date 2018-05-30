package com.example.shop.server.impl;

import com.example.shop.entity.ShopLabel;
import com.example.shop.repository.ShopLabelRepository;
import com.example.shop.server.ShopLabelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 标签实现
 */
@Service("shopLabelService")
public class ShopLabelServiceImpl implements ShopLabelService {

    @Resource
    private ShopLabelRepository shopLabelRepository;

    @Override
    public ShopLabel findShopLabelById(Integer id) {
        return shopLabelRepository.getOne(id);
    }

    @Override
    public void saveOrUpdateShopLabel(ShopLabel shopLabel) {
        shopLabelRepository.save(shopLabel);
    }

    @Override
    public void deleteShopLabelById(Integer id) {
        shopLabelRepository.deleteById(id);
    }
}
