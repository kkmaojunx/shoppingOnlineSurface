package com.example.shop.controller;

import com.example.shop.entity.*;
import com.example.shop.server.FileService;
import com.example.shop.server.ShopLabelService;
import com.example.shop.server.ShopTrolleyService;
import com.example.shop.server.ShoppingService;
import com.example.shop.util.DateUtil;
import com.example.shop.util.FileUtil;
import com.example.shop.util.ModelMerge;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javafx.scene.control.Label;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/shop")
public class ShoppingController {

    @Resource
    private ShoppingService shoppingService;
    @Resource
    private FileService fileService;
    @Resource
    private ShopLabelService shopLabelService;
    @Resource
    private ShopTrolleyService shopTrolleyService;

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
        if (shopping != null) {
            if (shopping.getCount() == null) {
                shopping.setCount(0);
            }
            shopping.setCount(Optional.ofNullable(shopping).map(c -> c.getCount()).orElse(0) + 1);
            shoppingService.saveShopping(shopping);
        }
        shopping.setActivity_img(shopping.getIpAddress() + shopping.getActivity_img());
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
        if (shoppingList != null) {
            Iterator<Shopping> shoppingIterator = shoppingList.listIterator();
            while (shoppingIterator.hasNext()) {
                Shopping shopping1 = shoppingIterator.next();
                if (shopping1.getImageurl().toArray().length > 0) {
                    File file = (File) shopping1.getImageurl().toArray()[0];
                    shopping1.setActivity_img(shopping1.getIpAddress() + file.getUrl());
                }
                shopping1.setImageurl(null);
                shopping1.setLabel(null);
                shopping1.setMerchantid(null);

            }
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "成功");
            stringObjectMap.put("info", shoppingList);
        } else {
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "成功");
            stringObjectMap.put("info", null);
        }
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
    public Map<String, Object> saveOrUpdate(Shopping shopping, @RequestParam(value = "activityImg", required = false) MultipartFile activityImg, HttpServletRequest request) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        shopping.setIpAddress(FileUtil.ipHttpAddress());
        // 活动配图
        if (shopping.getActivity_img() != null) {
            java.io.File file = new java.io.File(FileUtil.filePath(request), shopping.getActivity_img());
            if (file.exists()) {
                file.delete();
            }
        }
        if (activityImg != null) {
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
                }
                shopping.setActivity_img(filePath);
            }
        }
        if (shopping.getId() != null) {
            Shopping shopping1 = shoppingService.ShoppingById(shopping.getId());
            if (shopping.getTitle() != null && !"".equals(shopping.getTitle())) {
                shopping1.setTitle(shopping.getTitle());
            }
            if (shopping.getOldmoney() != null && !"".equals(shopping.getOldmoney())) {
                shopping1.setOldmoney(shopping.getOldmoney());
            }
            if (shopping.getRealmoney() != null && !"".equals(shopping.getRealmoney())) {
                shopping1.setRealmoney(shopping.getRealmoney());
            }
            if (shopping.getContent() != null && !"".equals(shopping.getContent())) {
                shopping1.setContent(shopping.getContent());
            }
            if (shopping.getHot() != null && !"".equals(shopping.getHot())) {
                shopping1.setHot(shopping.getHot());
            }
            if (shopping.getActivity_img() != null && !"".equals(shopping.getActivity_img())) {
                shopping1.setActivity_img(shopping.getActivity_img());
            }
            if (shopping.getActivity() != null && !"".equals(shopping.getActivity())) {
                shopping1.setActivity(shopping.getActivity());
            }
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
                stringObjectMap.put("info", shopping.getId());
            } else {
                stringObjectMap.put("code", 0);
                stringObjectMap.put("msg", "新增失败");
            }
        }
        return stringObjectMap;
    }

    /**
     * 修改or新增商品的图片
     *
     * @param file
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping("/image")
    public Map<String, Object> saveFileImage(File file, @RequestParam(value = "image", required = false) MultipartFile multipartFile, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (file.getUrl() != null) {
            java.io.File files = new java.io.File(FileUtil.filePath(request), file.getUrl());
            if (files.exists()) {
                files.delete();
            }
        }
        if (multipartFile != null) {
            if (!multipartFile.isEmpty()) {
                String filePath = FileUtil.fileMiddleLocal() + DateUtil.getDate(".jpg");
                java.io.File filess = new java.io.File(FileUtil.filePath(request), filePath);
                if (!filess.getParentFile().exists()) {
                    filess.getParentFile().mkdirs();
                }
                try {
                    multipartFile.transferTo(filess);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                file.setUrl(filePath);
            }
        }
        file.setIpAddress(FileUtil.ipHttpAddress());
        if (file.getId() != null) {
            File file1 = fileService.findFileById(file);
            if (file.getUrl() != null) {
                file1.setUrl(file.getUrl());
            }
            if (file.getName() != null) {
                file1.setName(file.getName());
            }
            file1.setIpAddress(FileUtil.ipHttpAddress());
            fileService.saveFile(file1);
            map.put("code", 1);
            map.put("msg", "图片修改成功");
        } else {
            fileService.saveFile(file);
            map.put("code", 1);
            map.put("msg", "图片新增成功");
        }
        return map;
    }

    /**
     * 删除文件
     *
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("/deleteShopImage")
    public Map<String, Object> deleteImage(File file, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        if (file.getUrl() != null) {
            java.io.File file1 = new java.io.File(request.getServletContext().getRealPath("/"), file.getUrl());
            if (file1.exists()) {
                file1.delete();
            }
        }
        if (file.getId() != null) {
            fileService.deleteFileById(file.getId());
            map.put("code", 1);
            map.put("msg", "文件删除成功");
        } else {
            map.put("code", 0);
            map.put("msg", "请传入文件的id");
        }
        return map;
    }

    /**
     * 新增or修改商品的标签
     *
     * @param shopLabel
     * @return
     */
    @RequestMapping("/label")
    public Map<String, Object> saveFileImage(ShopLabel shopLabel) {
        Map<String, Object> map = new HashMap<>();
        if (shopLabel.getId() != null) {
            ShopLabel shopLabel1 = shopLabelService.findShopLabelById(shopLabel.getId());
            if (shopLabel.getName() != null) {
                shopLabel1.setName(shopLabel.getName());
            }
            shopLabelService.saveOrUpdateShopLabel(shopLabel1);
            map.put("code", 1);
            map.put("msg", "修改商品标签成功");
        } else {
            shopLabelService.saveOrUpdateShopLabel(shopLabel);
            map.put("code", 1);
            map.put("msg", "新增商品标签成功");
        }
        return map;
    }

    /**
     * 删除商品标签通过商品标签的id
     *
     * @param shopLabel
     * @return
     */
    @RequestMapping("/deleteShopLabel")
    public Map<String, Object> deleteShopLabel(ShopLabel shopLabel) {
        Map<String, Object> map = new HashMap<>();
        if (shopLabel.getId() != null) {
            shopLabelService.deleteShopLabelById(shopLabel.getId());
            map.put("code", 1);
            map.put("msg", "删除商品标签成功");
        } else {
            map.put("code", 0);
            map.put("msg", "删除商品标签失败");
        }
        return map;
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
        ShopTrolley shopTrolley = new ShopTrolley();
        for (int i = 0; i < strings.length; i++) {
            Shopping shopping = new Shopping();
            shopping.setId(Integer.valueOf(strings[i]));
            shopTrolley.setShoppingid(shopping);
            List<ShopTrolley> shopTrolleys = shopTrolleyService.listShopTrolleyByShopId(shopTrolley);
            if (shopTrolleys != null) {
                Iterator iterator = shopTrolleys.iterator();
                while (iterator.hasNext()) {
                    ShopTrolley shopTrolley1 = (ShopTrolley) iterator.next();
                    shopTrolleyService.deleteShopTrolleyByShopId(shopTrolley1.getId());
                }
            }
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
     *
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
        Iterator iterator = shoppingList.iterator();
        while (iterator.hasNext()) {
            Shopping shopping1 = (Shopping) iterator.next();
            shopping1.setActivity_img(FileUtil.ipHttpAddress() + shopping1.getActivity_img());
        }
        stringObjectMap.put("code", 1);
        stringObjectMap.put("msg", "成功");
        stringObjectMap.put("info", shoppingList);
        return stringObjectMap;
    }

}
