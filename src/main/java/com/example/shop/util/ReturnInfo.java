package com.example.shop.util;

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
}
