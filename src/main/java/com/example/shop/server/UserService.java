package com.example.shop.server;

import com.example.shop.entity.User;

/**
 * 用户信息实体类
 */
public interface UserService {

    /**
     * 用户注册 or 修改
     * @param user
     */
    public void registerUser(User user);


    /**
     * 用户登陆
     * @param user
     * @return
     */
    public User findUser(User user);

    /**
     * 删除用户
     * @param user
     */
    public void deleteUser(User user);

    /**
     * 查询用户主页信息
     * @param user
     * @return
     */
    public User findUserMainInfo(User user);
}
