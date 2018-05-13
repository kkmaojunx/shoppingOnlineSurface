package com.example.shop.controller;

import com.example.shop.entity.Merchant;
import com.example.shop.server.MerchantService;
import com.example.shop.util.ModelMerge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 店铺Controller
 */
@Api(value = "店铺数据接口")
@RestController
@RequestMapping("/merchant")
public class MerchantController {

    @Resource
    private MerchantService merchantService;

    /**
     * 查询店铺通过用户的id
     *
     * @param merchant
     * @return
     */
    @ApiOperation(value = "查询用户的店铺", notes = "通过用户的id查询")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "userId", value = "用户的id", required = true, dataType = "Integer")
    )
    @RequestMapping(value = "/getByUserId", method = RequestMethod.POST)
    public Map<String, Object> getByUserId(Merchant merchant) {
        Map<String, Object> map = new HashMap<>();
        if (merchant.getUserId().getId() != null) {
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
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
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
            @ApiImplicitParam(name = "userId", value = "用户的id", required = false, dataType = "Integer")
    })
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Map<String, Object> saveOrUpdateMerchant(Merchant merchant) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        if (merchant.getId() != null) {
            Merchant merchant1 = merchantService.findOneMerchantByUserId(merchant);
            ModelMerge.modelMergeByModel(merchant1, merchant);
            merchantService.addMerchant(merchant1);
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "修改成功！");
        } else {
            merchantService.addMerchant(merchant);
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "添加成功！");
        }
        return stringObjectMap;
    }
}
