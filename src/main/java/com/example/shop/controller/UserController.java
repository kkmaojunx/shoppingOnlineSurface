package com.example.shop.controller;

import com.example.shop.entity.ShopTrolley;
import com.example.shop.entity.User;
import com.example.shop.server.ShopTrolleyService;
import com.example.shop.server.UserService;
import com.example.shop.util.FileUtil;
import com.example.shop.util.ResponseUtil;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息Controller类
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    @Resource
    private ShopTrolleyService shopTrolleyService;

    /**
     * 用户注册 或者修改
     *
     * @param user     用户数据  username password or id
     * @param response
     * @throws Exception
     */
    @RequestMapping("/register")
    public void registerUser(User user, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        User user1 = userService.findUser(user);
        if (user.getId() != null) {
            userService.registerUser(user1);
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功");
        } else {
            if (user.getUsername() != null && user.getPassword() != null) {
                if (user1 != null) {
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "账号已经存在");
                } else {
                    userService.registerUser(user);
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "注册成功");
                }
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "请输入用户名和密码");
            }
        }
        ResponseUtil.write(response, jsonObject);
    }

    /**
     * 用户登陆
     *
     * @param user     用户数据  username password
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping("/login")
    public void loginUser(User user, HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (user != null && user.getUsername() != null && user.getPassword() != null) {
            User user1 = userService.findUser(user);
            if (user1 != null && user1.getUsername().equals(user.getUsername()) && user1.getPassword().equals(user.getUsername())) {
                jsonObject.put("code", 1);
                jsonObject.put("msg", "登陆成功");
                jsonObject.put("info", user1.getId());
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "登陆失败，请输入正确的的用户名或者密码");
            }
        } else {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "请输入用户名和密码");
        }
        ResponseUtil.write(response, jsonObject);
    }

    /**
     * 用户信息删除
     *
     * @param user     userid
     * @param response
     * @throws Exception
     */
    @RequestMapping("/delete")
    public void deleteUser(User user, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "请输入用户id");
            ResponseUtil.write(response, jsonObject);
            return;
        }
        userService.deleteUser(user);
        jsonObject.put("code", 1);
        jsonObject.put("msg", "删除成功");
        ResponseUtil.write(response, jsonObject);
    }

    /**
     * 用户主页查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/userInfo")
    public Map<String, Object> findUserInfo(@RequestParam(value = "id", required = false) Integer id, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        User user = new User();
        user.setId(id);
        user = userService.findUserMainInfo(user);
        ShopTrolley shopTrolley = new ShopTrolley();
        shopTrolley.setUserid(user);
        shopTrolley.setBuy(1);
        user.setAlreadyBuy(Integer.valueOf(String.valueOf(shopTrolleyService.alreadyBuyTotal(shopTrolley))));
        shopTrolley.setBuy(0);
        user.setShopBus(Integer.valueOf(String.valueOf(shopTrolleyService.alreadyBuyTotal(shopTrolley))));
        shopTrolley.setBuy(3);
        user.setObjectFlowIndent(Integer.valueOf(String.valueOf(shopTrolleyService.alreadyBuyTotal(shopTrolley))));
        stringObjectMap.put("code", 1);
        stringObjectMap.put("msg", "成功");
        stringObjectMap.put("info", user);
        return stringObjectMap;
    }

    /**
     * 用户修改头像以及背景图片
     *
     * @param multipartFile
     * @param request
     * @return
     */
    @RequestMapping("/updateImage")
    public Map<String, Object> updateImage(@RequestParam("file") MultipartFile multipartFile, User user, HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<>();
        if (multipartFile.isEmpty()) {
            map.put("code", 0);
            map.put("msg", "请选择文件再进行上传");
            return map;
        }
//        对内使用的路径地址
        String filePath = request.getServletContext().getRealPath("/");
//        删除文件
        String uploadFileName = null;
        if (user.getImageHead() != null) {
            uploadFileName = user.getImageHead();
        } else if (user.getImageBackground() != null) {
            uploadFileName = user.getImageBackground();
        }
        java.io.File files = new java.io.File(filePath, uploadFileName);
        if (files.exists()) {
            files.delete();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHssmm");
        String string = simpleDateFormat.format(new Date());
//        图片名字
        String fileName = FileUtil.fileMiddleLocal() + string + ".jpg";
        java.io.File file = new java.io.File(filePath, fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            multipartFile.transferTo(new java.io.File(filePath + java.io.File.separator + fileName));
            HttpSession session = request.getSession();
            User sessionUser = (User) session.getAttribute("user");
            user.setId(sessionUser.getId());
            User user1 = userService.findUserMainInfo(user);
            user1.setIpAddress(FileUtil.ipHttpAddress());
            if (user.getImageHead() != null) {
                user1.setImageHead(fileName);
            } else if (user.getImageBackground() != null) {
                user1.setImageBackground(fileName);
            }
            userService.registerUser(user1);
            map.put("code", 1);
            map.put("msg", "文件上传成功");
            map.put("info", FileUtil.ipHttpAddress() + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("code", 0);
            map.put("msg", "失败，请重试");
        }
        return map;
    }
}
