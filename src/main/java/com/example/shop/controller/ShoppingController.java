package com.example.shop.controller;

import com.example.shop.entity.File;
import com.example.shop.entity.Shopping;
import com.example.shop.server.ShoppingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShoppingController {

    @Resource
    private ShoppingService shoppingService;

    /**
     * 查询单个商品通过商品id
     * @param id    要查询的商品id
     * @return
     */
    @RequestMapping("/shopById")
    public Map<String, Object> shoppingById(@RequestParam(value = "id") Integer id) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        Shopping shopping = shoppingService.ShoppingById(id);
        stringObjectMap.put("code", 1);
        stringObjectMap.put("msg", "成功");
        stringObjectMap.put("info", shopping);
        return stringObjectMap;
    }

    /**
     * 活动商品查询
     * @return
     */
    @RequestMapping("/activity")
    public Map<String, Object> shopActivity() {
        Shopping shopping = new Shopping();
        shopping.setActivity(1);
        List<Shopping> shoppingList = shoppingService.activityShoppingList(shopping);
        System.out.printf(shoppingList.toString());
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("code", 1);
        stringObjectMap.put("msg", "成功");
        stringObjectMap.put("info", shoppingList);
        return stringObjectMap;
    }

    /**
     * 搜索框模糊搜索商品
     * @param shopping  商品名字关键字
     * @return
     */
    @RequestMapping("/search")
    public Map<String, Object> searchShop(Shopping shopping) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        if (shopping == null) {
            stringObjectMap.put("code", 0);
            stringObjectMap.put("msg", "请输入或者选择条件再进行查询");
            return stringObjectMap;
        }
        List<Shopping> shoppingList = shoppingService.searchShoppingList(shopping);
        Iterator<Shopping> shoppingIterator =  shoppingList.listIterator();
        while (shoppingIterator.hasNext()) {
            Shopping shopping1 = shoppingIterator.next();
            File file = (File) shopping1.getImageurl().toArray()[0];
            shopping1.setActivity_img(file.getUrl());
            shopping1.setImageurl(null);
            shopping1.setLabel(null);
            shopping1.setMerchantid(null);
        }
        stringObjectMap.put("code", 1);
        stringObjectMap.put("msg", "成功");
        stringObjectMap.put("info", shoppingList);
        return stringObjectMap;
    }
}
