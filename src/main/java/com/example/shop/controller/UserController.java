package com.example.shop.controller;

import com.example.shop.entity.ObjectFlowAddress;
import com.example.shop.entity.ShopTrolley;
import com.example.shop.entity.User;
import com.example.shop.server.ShopTrolleyService;
import com.example.shop.server.UserService;
import com.example.shop.util.FileUtil;
import com.example.shop.util.ModelMerge;
import com.example.shop.util.ResponseUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
     * 用户注册 或者修改 ，有id就是修改，没有id就是注册
     *
     * @param user     用户数据  username password or id
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "用户注册或者修改", notes = "如果id不为null就为修改，否则为注册")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户数据实体类", required = true, dataType = "User"),
            @ApiImplicitParam(name = "rePassword", value = "修改密码的确认密码", required = false, dataType = "String")
    })
    @RequestMapping(value = "/register")
    public void registerUser(User user, @RequestParam(value = "repetition", required = false) String rePassword, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        User user1 = userService.findUser(user);
        if (user.getId() != null) {
            if (user.getPassword() == null && rePassword == null) {
                System.out.println("user1："+user1);
                System.out.println("user："+user);
                // 合并模型
                ModelMerge.modelMergeByModel(user1, user);
                userService.registerUser(user1);
                jsonObject.put("code", 1);
                jsonObject.put("msg", "修改成功");
            } else {
                if (user.getPassword() != null && rePassword != null && user.getPassword().equals(rePassword)) {
                    // 合并模型
                    ModelMerge.modelMergeByModel(user1, user);
                    userService.registerUser(user1);
                    jsonObject.put("code", 1);
                    jsonObject.put("msg", "密码修改成功");
                } else {
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "密码修改失败，请检查后重新输入");
                }
            }
        } else {
            if (user.getUsername() != null && user.getPassword() != null && user.getUserStatus() != null) {
                if (user1 != null) {
                    jsonObject.put("code", 0);
                    jsonObject.put("msg", "账号已经存在");
                } else {
                    user.setBirthday(new Date());
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
    @ApiOperation(value = "用户登陆", notes = "通过用户账号密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户登陆数据实体类", required = true, dataType = "User")
    })
    @RequestMapping(value = "/login")
    public void loginUser(@Valid User user, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (bindingResult.hasErrors()) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", bindingResult.getFieldError().getDefaultMessage());
        } else {
            User user1 = userService.findUser(user);
            if (user1 != null && user1.getUsername().equals(user.getUsername()) && user1.getPassword().equals(user.getUsername())) {
                jsonObject.put("code", 1);
                jsonObject.put("msg", "登陆成功");
                jsonObject.put("info", user1.getId());
                jsonObject.put("userStatus", user1.getUserStatus());
            } else {
                jsonObject.put("code", 0);
                jsonObject.put("msg", "登陆失败，请输入正确的的用户名或者密码");
            }
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
    @ApiOperation(value = "删除用户", notes = "通过用户id删除用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "删除用户的数据实体类", required = true, dataType = "User")
    })
    @RequestMapping(value = "/delete")
    public void deleteUser(User user, HttpServletResponse response) throws Exception {
        JSONObject jsonObject = new JSONObject();
        if (user == null) {
            jsonObject.put("code", 0);
            jsonObject.put("msg", "请输入用户id");
            ResponseUtil.write(response, jsonObject);
            return;
        }
        System.out.println(user);
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
    @ApiOperation(value = "查询用户的所有信息", notes = "通过用户的id进行查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户的id", required = true, dataType = "Integer")
    })
    @RequestMapping(value = "/userInfo")
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
        user.setHeadLocal(user.getIpAddress() + user.getImageHead());
        user.setBackgroundLocal(user.getIpAddress() + user.getImageBackground());
        stringObjectMap.put("code", 1);
        stringObjectMap.put("msg", "成功");
        if (user.getBirthday() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(user.getBirthday());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            user.setBirthday(calendar.getTime());
        }
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
    @ApiOperation(value = "修改用户的头像或者背景图片", notes = "通过字段imageHead则修改用户的头像，通过字段imageBackground则修改用户的背景图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "multipartFile", value = "上传的文件", required = true, dataType = "MultipartFile"),
            @ApiImplicitParam(name = "user", value = "用户的图片路径", required = true, dataType = "User")
    })
    @RequestMapping(value = "/updateImage", method = RequestMethod.POST)
    public Map<String, Object> updateImage(@RequestParam("file") MultipartFile multipartFile, User user, HttpServletRequest request) throws Exception {
        System.out.println(multipartFile);
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

    /**
     * 修改密码，验证原密码对不对
     * @param user
     * @return
     */
    @ApiOperation(value = "修改密码的时候验证密码是否正确", notes = "修改密码的原密码需要确认本人登陆，需要输入密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "用户的密码和用户的id", required = true, dataType = "User")
    })
    @RequestMapping(value = "/validPassword")
    public Map<String, Object> validPasswordIsOk(User user) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            stringObjectMap.put("code", 0);
            stringObjectMap.put("msg", "原密码未输入，请输入原密码");
        } else {
            User user1 = userService.findUser(user);
            if (user1.getPassword().equals(user.getPassword())) {
                stringObjectMap.put("code", 1);
                stringObjectMap.put("msg", "密码验证正确，请继续下一步");
            } else {
                stringObjectMap.put("code", 0);
                stringObjectMap.put("msg", "原密码错误，请重新输入");
            }
        }
        return stringObjectMap;
    }

}
