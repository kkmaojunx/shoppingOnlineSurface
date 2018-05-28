package com.example.shop.controller;

import com.example.shop.entity.Merchant;
import com.example.shop.entity.User;
import com.example.shop.server.MerchantService;
import com.example.shop.util.DateUtil;
import com.example.shop.util.FileUtil;
import com.example.shop.util.ModelMerge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 店铺Controller
 */
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Resource
    private MerchantService merchantService;

    /**
     * 查询店铺通过用户的id
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "查询用户的店铺", notes = "通过用户的id查询")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "Integer")
    )
    @RequestMapping("/getByUserId")
    public Map<String, Object> getByUserId(@RequestParam(value = "id", required = true) Integer id) {
        Map<String, Object> map = new HashMap<>();
        if (id != null) {
            Merchant merchant = new Merchant();
            User user = new User();
            user.setId(id);
            merchant.setUserId(user);
            map.put("code", 1);
            map.put("msg", "成功");
            map.put("info", merchantService.findOneMerchantByUserId(merchant));
        } else {
            map.put("code", 0);
            map.put("msg", "错误，查询请发送用户id过来");
        }
        return map;
    }

    /**
     * 删除店铺通过店铺的id
     *
     * @param merchant
     * @return
     */
    @ApiOperation(value = "删除用户的店铺", notes = "通过用户的店铺id")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "id", value = "用户的店铺id", required = true, dataType = "Integer")
    )
    @RequestMapping("/delete")
    public Map<String, Object> deleteUserMerchantById(Merchant merchant) {
        Map<String, Object> map = new HashMap<>();
        if (merchant != null) {
            merchantService.deleteMerchantById(merchant.getId());
            map.put("code", 1);
            map.put("msg", "删除成功");
        } else {
            map.put("code", 0);
            map.put("msg", "错误，删除失败");
        }
        return map;
    }

    /**
     * 添加店铺或者修改店铺
     *
     * @param merchant
     * @return
     */
    @ApiOperation(value = "新增或者修改店铺", notes = "修改用过店铺的id，新增通过用户的id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "店铺的id", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "userId", value = "用户的id", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "image", value = "上传的图片", required = false, dataType = "File")
    })
    @RequestMapping("/save")
    public Map<String, Object> saveOrUpdateMerchant(Merchant merchant, @RequestParam(value = "userId", required = false) Integer userId, @RequestParam(value = "image", required = false) MultipartFile multipartFile, HttpServletRequest request) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        if (merchant.getImageHead() != null) {
            File file = new File(FileUtil.fileMiddleLocal() + merchant.getImageHead());
            if (file.exists()) {
                file.delete();
            }
        }
        if (!multipartFile.isEmpty()) {
            // 生成文件上传的路径
            String filePath = FileUtil.filePath(request) + FileUtil.fileMiddleLocal();
            String fileName = DateUtil.getDate(".jpg");
            File file = new File(filePath, fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                multipartFile.transferTo(new File(filePath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            merchant.setImageHead(FileUtil.fileMiddleLocal() + fileName);
        }
        if (merchant.getId() != null) {
            Merchant merchant1 = merchantService.findOneMerchantByUserId(merchant);
            if (merchant.getName() != null) {
                merchant1.setName(merchant.getName());
            }
            if (merchant.getContent() != null) {
                merchant1.setContent(merchant.getContent());
            }
            if (merchant.getImageHead() != null) {
                merchant1.setImageHead(merchant.getImageHead());
            }
            merchantService.addMerchant(merchant1);
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "修改成功！");
        } else {
            if (userId != null) {
                User user = new User();
                user.setId(userId);
                merchant.setUserId(user);
                Merchant merchant1 = merchantService.findOneMerchantByUserId(merchant);
                if (merchant1 != null) {
                    stringObjectMap.put("code", 0);
                    stringObjectMap.put("msg", "添加失败，每个卖家只能有一个店铺");
                } else {
                    merchantService.addMerchant(merchant);
                    stringObjectMap.put("code", 1);
                    stringObjectMap.put("msg", "添加成功！");
                }
            } else {
                stringObjectMap.put("code", 0);
                stringObjectMap.put("msg", "添加失败，请输入用户id");
            }
        }
        return stringObjectMap;
    }
}
