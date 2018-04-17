package com.example.shop.controller;

import com.example.shop.entity.User;
import com.example.shop.server.UserService;
import com.example.shop.util.ResponseUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户信息Controller类
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

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
        if (user.getId() != null) {
            userService.registerUser(user);
            jsonObject.put("code", 1);
            jsonObject.put("msg", "修改成功");
        } else {
            if (user.getUsername() != null && user.getPassword() != null) {
                User user1 = userService.findUser(user);
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
                HttpSession session = request.getSession();
                session.setAttribute("user", user1);
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
}
