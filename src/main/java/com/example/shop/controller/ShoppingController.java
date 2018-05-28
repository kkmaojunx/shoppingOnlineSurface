package com.example.shop.controller;

import com.example.shop.entity.File;
import com.example.shop.entity.Merchant;
import com.example.shop.entity.Shopping;
import com.example.shop.server.ShoppingService;
import com.example.shop.util.DateUtil;
import com.example.shop.util.FileUtil;
import com.example.shop.util.ModelMerge;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
     *
     * @param id 要查询的商品id
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
     *
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
     *
     * @param shopping 商品名字关键字
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
        Iterator<Shopping> shoppingIterator = shoppingList.listIterator();
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

    /**
     * 新增商品or修改商品
     *
     * @param shopping
     * @return
     */
    @ApiOperation(value = "新增商品or修改商品", notes = "通过商品id来判断是否新增商品")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "商品的id", required = false, dataType = "Integer")
    )
    @RequestMapping("/save")
    public Map<String, Object> saveOrUpdate(Shopping shopping, File[] files, @RequestParam(value = "activityImg", required = false) MultipartFile activityImg , @RequestParam(value = "file", required = false) MultipartFile[] multipartFiles, HttpServletRequest request) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        shopping.setIpAddress(FileUtil.ipHttpAddress());
        // 活动配图
        if (shopping.getActivity_img() != null) {
            java.io.File file = new java.io.File(FileUtil.filePath(request), shopping.getActivity_img());
            if (file.exists()) {
                file.delete();
            }
        }
        if (!activityImg.isEmpty()) {
            String filePath = FileUtil.fileMiddleLocal() + DateUtil.getDate(".jpg");
            java.io.File file = new java.io.File(FileUtil.filePath(request), filePath);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                activityImg.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("上传错误信息：" + e.getMessage());
            }
            shopping.setActivity_img(filePath);
        }
        //图片文件
        if (multipartFiles != null && multipartFiles.length > 0) {
            if (shopping.getImageurl() != null) {
                for (MultipartFile f :
                        multipartFiles) {

                }
            } else {

            }
        }
        if (shopping.getId() != null) {
            Shopping shopping1 = shoppingService.ShoppingById(shopping.getId());
            ModelMerge.modelMergeByModel(shopping1, shopping);
            boolean b = shoppingService.saveShopping(shopping1);
            if (b) {
                stringObjectMap.put("code", 1);
                stringObjectMap.put("msg", "修改成功");
            } else {
                stringObjectMap.put("code", 0);
                stringObjectMap.put("msg", "修改失败");
            }
        } else {
            boolean b = shoppingService.saveShopping(shopping);
            if (b) {
                stringObjectMap.put("code", 1);
                stringObjectMap.put("msg", "新增成功");
            } else {
                stringObjectMap.put("code", 0);
                stringObjectMap.put("msg", "新增失败");
            }
        }
        return stringObjectMap;
    }

    /**
     * 删除商品
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "删除商品", notes = "通过商品的id")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "ids", value = "id集", required = true, dataType = "String")
    )
    @RequestMapping("/delete")
    public Map<String, Object> deleteShopById(@RequestParam(value = "ids", required = true) String ids) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        String[] strings = ids.split(",");
        Integer count = 0;
        for (int i = 0; i < strings.length; i++) {
            boolean b = shoppingService.deleteShoppingById(Integer.valueOf(strings[i]));
            if (b) {
                count++;
            }
        }
        if (count == 0) {
            stringObjectMap.put("code", 0);
            stringObjectMap.put("msg", "删除失败，没有删除任何数据");
        } else if (count < strings.length) {
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "删除成功，部分数据删除成功");
        } else {
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "删除成功，所选数据完成删除");
        }
        return stringObjectMap;
    }

    /**
     * 查询商家商品
     * @param merchant
     * @return
     */
    @ApiOperation(value = "查询商家商品", notes = "通过商家的id")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "商家的id", required = false, dataType = "Integer")
    )
    @RequestMapping("/list")
    public Map<String, Object> findAllShopByMerchantId(Merchant merchant) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        Shopping shopping = new Shopping();
        shopping.setMerchantid(merchant);
        List<Shopping> shoppingList = shoppingService.listShopByMerchantId(shopping);
        stringObjectMap.put("code", 1);
        stringObjectMap.put("msg", "成功");
        stringObjectMap.put("info", shoppingList);
        return stringObjectMap;
    }

}
