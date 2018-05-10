package com.example.shop.server;

import com.example.shop.entity.ObjectFlowAddress;

import java.util.List;

/**
 * 收货地址的service接口
 */
public interface ObjectFlowAddressService {

    /**
     * 获取所有的收货地址通过用户的id
     * @param objectFlowAddress
     * @return
     */
    public List<ObjectFlowAddress> listAllAddress(ObjectFlowAddress objectFlowAddress);

    /**
     * 通过id删除地址
     * @param id
     */
    public void addressDelete(Integer id);

    /**
     * 新增用户地址 or 修改
     * @param objectFlowAddress
     */
    public void addressSave(ObjectFlowAddress objectFlowAddress);

    /**
     * 通过id查询一条收货地址
     * @param id
     * @return
     */
    public ObjectFlowAddress findOneAddress(Integer id);

}
