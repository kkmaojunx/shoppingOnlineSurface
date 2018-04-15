package com.example.shop.controller;

import com.example.shop.server.ShopTrolleyService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/trolley")
public class ShopTrolleyController {

    @Resource
    private ShopTrolleyService shopTrolleyService;

    // 通过用户id查询购物车商品

    // 相购物车添加商品通过商品
}
