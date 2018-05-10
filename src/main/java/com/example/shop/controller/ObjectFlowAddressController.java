package com.example.shop.controller;

import com.example.shop.entity.ObjectFlowAddress;
import com.example.shop.entity.User;
import com.example.shop.server.ObjectFlowAddressService;
import com.example.shop.util.ModelMerge;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 收获地址Controller
 */
@RestController
@RequestMapping("/address")
public class ObjectFlowAddressController {

    @Resource
    private ObjectFlowAddressService objectFlowAddressService;


    /**
     * 查询用户的收获地址，通过用户的id
     *
     * @param objectFlowAddress
     * @return
     */
    @ApiOperation(value = "查询用户的收货地址", notes = "通过用户的id")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectFlowAddress", value = "地址实体类", required = true, dataType = "ObjectFlowAddress")
    })
    @RequestMapping(value = "/list")
    public Map<String, Object> acquireAddress(ObjectFlowAddress objectFlowAddress) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        if (objectFlowAddress.getUserId() == null) {
            stringObjectMap.put("code", 0);
            stringObjectMap.put("msg", "请发送用户的id过来");
        } else {
            List<ObjectFlowAddress> objectFlowAddresses = objectFlowAddressService.listAllAddress(objectFlowAddress);
            Iterator<ObjectFlowAddress> iterator = objectFlowAddresses.iterator();
            while (iterator.hasNext()) {
                ObjectFlowAddress objectFlowAddress1 = iterator.next();
                User user = new User();
                user.setId(objectFlowAddress1.getUserId().getId());
                objectFlowAddress1.setUserId(user);
            }
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "成功");
            stringObjectMap.put("info", objectFlowAddresses);
        }
        return stringObjectMap;
    }

    /**
     * 删除用户的收货地址 通过id
     *
     * @param ids id or id[]
     * @return
     */
    @RequestMapping("/delete")
    public Map<String, Object> addressDelete(@RequestParam(value = "ids", required = false) String ids) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        if (ids == null || "".equals(ids)) {
            stringObjectMap.put("code", 0);
            stringObjectMap.put("msg", "请发送收获地址的id过来");
        } else {
            String[] strings = ids.split(",");
            for (int i = 0; i < strings.length; i++) {
                objectFlowAddressService.addressDelete(Integer.valueOf(strings[i]));
            }
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "收获地址删除成功");
        }
        return stringObjectMap;
    }

    /**
     * 新增或者修改用户的收货地址
     *
     * @param objectFlowAddress
     * @param bindingResult
     * @return
     */
    @RequestMapping("/save")
    public Map<String, Object> addressSave(@Valid ObjectFlowAddress objectFlowAddress, BindingResult bindingResult) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        if (bindingResult.hasErrors()) {
            stringObjectMap.put("code", 0);
            stringObjectMap.put("msg", bindingResult.getFieldError().getDefaultMessage());
        } else {
            if (objectFlowAddress.getId() != null) {
                objectFlowAddressService.addressSave(objectFlowAddress);
                stringObjectMap.put("code", 1);
                stringObjectMap.put("msg", "收货地址修改成功！");
            } else {
                objectFlowAddressService.addressSave(objectFlowAddress);
                stringObjectMap.put("code", 1);
                stringObjectMap.put("msg", "收货地址新增成功！");
            }
        }
        return stringObjectMap;
    }

    /**
     * 查询一个收货地址
     *
     * @param id 查询的收货地址id
     * @return
     */
    @RequestMapping("/oneAddress")
    public Map<String, Object> findOneAddress(@RequestParam(value = "id", required = false) Integer id) {
        Map<String, Object> stringObjectMap = new HashMap<>();
        if (id == null) {
            stringObjectMap.put("code", 0);
            stringObjectMap.put("msg", "请选择id");
        } else {
            ObjectFlowAddress objectFlowAddress = objectFlowAddressService.findOneAddress(id);
            stringObjectMap.put("code", 1);
            stringObjectMap.put("msg", "成功");
            stringObjectMap.put("info", objectFlowAddress);
        }
        return stringObjectMap;
    }
}
