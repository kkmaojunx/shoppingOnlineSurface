package com.example.shop.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 获取时间工具类
 */
public class DateUtil {

    /**
     * 获得文件上传的时间
     * @return
     */
    public static String getDate(String s) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHssmm");
        return simpleDateFormat.format(new Date()) + s;
    }
}
