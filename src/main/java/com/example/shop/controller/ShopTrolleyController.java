package com.example.shop.controller;

import com.example.shop.entity.ShopLabel;
import com.example.shop.entity.ShopTrolley;
import com.example.shop.entity.User;
import com.example.shop.server.ShopTrolleyService;
import com.example.shop.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/trolley")
public class ShopTrolleyController {

    @Resource
    private ShopTrolleyService shopTrolleyService;

    /**
     * 通过用户id查询购物车信息
     * @param trolley
     * @param response
     * @throws Exception
     */
    @RequestMapping("/list")
    public Map<String, Object> searchUserCartList(ShopTrolley trolley, HttpServletResponse response) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (trolley.getUserid().getId() == null || "".equals(trolley.getUserid().getId()) || trolley.getBuy() == null) {
            map.put("code", 0);
            map.put("msg", "请输入用户id进行查询");
        } else {
            List<ShopTrolley> shopTrolleys = shopTrolleyService.ShopTrolleyByUserId(trolley);
            Iterator<ShopTrolley> shopTrolleyIterator = shopTrolleys.iterator();
            while (shopTrolleyIterator.hasNext()) {
                ShopTrolley shopTrolley = shopTrolleyIterator.next();
                shopTrolley.setTrolleyImg(shopTrolley.getShoppingid().getActivity_img());
                shopTrolley.setShoptitle(shopTrolley.getShoppingid().getTitle());
                shopTrolley.setMoney(shopTrolley.getShoppingid().getRealmoney());
                shopTrolley.setLableName(shopTrolley.getShoplabelid().getName());
                shopTrolley.setUserid(null);
                shopTrolley.setShoppingid(null);
                shopTrolley.setShoplabelid(null);
            }
            map.put("code", 1);
            map.put("msg", "成功");
            map.put("info", shopTrolleys);
        }
        return map;
    }

    /**
     * 添加商品到购物车
     * @param shopTrolley   userid用户id      shopid商品id  buy 1
     * @param labelId       标签id
     * @param response
     * @throws Exception
     */
    @RequestMapping("/add")
    public void addUserCartShop(ShopTrolley shopTrolley, Integer labelId, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (shopTrolley == null || shopTrolley.getShoppingid().getId() == null || labelId == null) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "失败，请输入商品id或者选择商品类别");
        } else {
            ShopLabel shopLabel = new ShopLabel();
            shopLabel.setId(labelId);
            shopTrolley.setShoplabelid(shopLabel);
            shopTrolleyService.appendShopToShopTrolley(shopTrolley);
            jsonObject.put("code", 1);
            jsonObject.put("msg", "添加成功");
        }
        ResponseUtil.write(response, jsonObject);
    }

    /**
     * 删除购物车的商品通过id
     * @param id     购物车id
     * @param response
     */
    @RequestMapping("/delete")
    public void deleteUserCartShop(@RequestParam(value = "id", required = true) Integer id, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (id == null || id == 0) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "失败，请输入商品id或者选择商品类别");
        } else {
            try {
                shopTrolleyService.removeShopTrolleyById(id);
                jsonObject.put("code", 1);
                jsonObject.put("msg", "删除成功");
            } catch (Exception e) {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "失败，无效的删除");
            }
        }
        ResponseUtil.write(response, jsonObject);
    }

    /**
     * 购买购物车的商品
     * @param ids   通过id数组
     * @param response
     * @throws Exception
     */
    @RequestMapping("/buy")
    public void buyShopByShopTrolleyId(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (ids == null || "".equals(ids)) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "失败，请选择你要购买的商品");
        } else {
            String[] strings = ids.split(",");
            for (int i = 0; i < strings.length; i++) {
                ShopTrolley shopTrolley = new ShopTrolley();
                shopTrolley.setId(Integer.parseInt(strings[i]));
                shopTrolley.setBuy(1);
                try {
                    shopTrolleyService.appendShopToShopTrolley(shopTrolley);
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "购买成功");
                } catch (Exception e) {
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "部分商品购买失败，请重试");
                    break;
                }
            }
        }
        ResponseUtil.write(response, jsonObject);
    }

}
