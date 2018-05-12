package com.example.shop.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 文件操作类
 */
public class FileUtil {

    /**
     * 获取主机的ip地址
     *
     * @return
     */
    public static String ipHttpAddress() throws UnknownHostException {
        return "http://" + InetAddress.getByName("www.zhangdanling.cn").getHostAddress()+"/";
    }

    public static String fileMiddleLocal() {
        return "static/images/";
    }
}
