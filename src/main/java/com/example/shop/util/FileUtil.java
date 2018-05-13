package com.example.shop.util;

import javax.servlet.http.HttpServletRequest;
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
    public static String ipHttpAddress() {
        String ip = null;
        try {
            ip = "http://" + InetAddress.getLocalHost().getHostAddress() + "/";
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.out.println("错误：IP地址报错===" + e.getMessage());
        }
        return ip;
    }

    /**
     * 获得当前的文件主路径
     *
     * @return
     */
    public static String fileMiddleLocal() {
        return "static/images/";
    }

    /**
     * 返回服务器当前路径
     *
     * @param request
     * @return
     */
    public static String filePath(HttpServletRequest request) {
        return request.getServletContext().getRealPath("/");
    }
}
