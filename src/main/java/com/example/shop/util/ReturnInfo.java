package com.example.shop.util;

import com.example.shop.entity.User;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回Map数据
 */
public class ReturnInfo {

    public static Map<String, Object> returnInfo(List<Object> list) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        stringObjectMap.put("code", 1);
        stringObjectMap.put("msg", "成功");
        stringObjectMap.put("info", list);
        return stringObjectMap;
    }

    public static void main(String[] args) {
        User user = new User();

        User user1 = new User();
        user1.setId(1);
        user1.setHeadLocal("www.zhangdanling.cn");
        user1.setBirthday(new Date());
        user1.setObjectFlowIndent(1);

        User user2 = user1;

        ModelMerge.mergeObject(user, user2);

        System.out.printf(user.toString());
    }
}
