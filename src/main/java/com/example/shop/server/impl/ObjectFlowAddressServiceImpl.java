package com.example.shop.server.impl;

import com.example.shop.entity.ObjectFlowAddress;
import com.example.shop.repository.ObjectFlowAddressRepository;
import com.example.shop.server.ObjectFlowAddressService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 收货地址service接口实现类
 */
@Service("objectFlowAddressService")
public class ObjectFlowAddressServiceImpl implements ObjectFlowAddressService {

    @Resource
    private ObjectFlowAddressRepository objectFlowAddressRepository;

    /**
     * 获取所有的收获地址
     * @param objectFlowAddress 用户id
     * @return
     */
    @Override
    public List<ObjectFlowAddress> listAllAddress(ObjectFlowAddress objectFlowAddress) {
        List<ObjectFlowAddress> objectFlowAddresses = objectFlowAddressRepository.findAll(new Specification<ObjectFlowAddress>() {
            @Override
            public Predicate toPredicate(Root<ObjectFlowAddress> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (objectFlowAddress != null) {
                    if (objectFlowAddress.getUserId().getId() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("userId"), objectFlowAddress.getUserId().getId()));
                    }
                }
                return predicate;
            }
        });
        return objectFlowAddresses;
    }

    /**
     * 删除用户地址通过id
     * @param id id
     */
    @Override
    public void addressDelete(Integer id) {
        objectFlowAddressRepository.deleteById(id);
    }

    /**
     * 新增用户地址or修改用户地址通过id
     * @param objectFlowAddress
     */
    @Override
    public void addressSave(ObjectFlowAddress objectFlowAddress) {
        objectFlowAddressRepository.save(objectFlowAddress);
    }

    /**
     * 通过收货地址id查询收货地址的详细信息
     * @param id
     * @return
     */
    @Override
    public ObjectFlowAddress findOneAddress(Integer id) {
        return objectFlowAddressRepository.getOne(id);
    }
}
