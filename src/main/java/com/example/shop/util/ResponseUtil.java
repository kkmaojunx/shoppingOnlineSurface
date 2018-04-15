package com.example.shop.util;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 输出流
 */
public class ResponseUtil {

    /**
     * 通过流输出
     * @param response
     * @param o
     * @throws Exception
     */
    public static void write(HttpServletResponse response, Object o) throws Exception {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.println(o.toString());
        printWriter.flush();
        printWriter.close();
    }
}
