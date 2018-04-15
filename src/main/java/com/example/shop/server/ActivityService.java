package com.example.shop.server;

import com.example.shop.entity.Activity;

import java.util.List;

/**
 * 活动service接口
 */
public interface ActivityService {

    /**
     * 活动查询接口
     * @return
     */
    public List<Activity> activityList(Integer id);
}
