package com.example.shop.controller;

import com.example.shop.entity.Activity;
import com.example.shop.entity.Shopping;
import com.example.shop.server.ActivityService;
import com.example.shop.server.ShoppingService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.*;

/**
 * 活动类controller
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Resource
    private ActivityService activityService;
    @Resource
    private ShoppingService shoppingService;

    /**
     * 查询所有活动
     *
     * @return
     */
    @RequestMapping("/list")
    public Map<String, Object> activityList(@RequestParam(value = "id", required = false) Integer id) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        Shopping shopping = new Shopping();
        shopping.setActivity(id);
        List<Shopping> shoppingList = shoppingService.activityShoppingList(shopping);
        List<Activity> activities = new ArrayList<>();
        if (shoppingList != null && shoppingList.size() > 0) {
            Iterator iterator = shoppingList.iterator();
            while (iterator.hasNext()) {
                Shopping shopping1 = (Shopping) iterator.next();
                if (shopping1 != null) {
                    Activity activity = new Activity();
                    activity.setShopid(shopping1.getId());
                    activity.setImageUrl(shopping1.getIpAddress() + shopping1.getActivity_img());
                    activities.add(activity);
                }
            }
        }
        stringObjectMap.put("code", 1);
        stringObjectMap.put("msg", "成功");
        stringObjectMap.put("info", activities);
        return stringObjectMap;
    }
}
