package com.example.shop.server.impl;

import com.example.shop.entity.ShopTrolley;
import com.example.shop.entity.Shopping;
import com.example.shop.entity.User;
import com.example.shop.repository.ShopTrolleyRepository;
import com.example.shop.server.ShopTrolleyService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 购物车service接口实现类
 */
@Service("shopTrolleyService")
public class ShopTrolleyServiceImpl implements ShopTrolleyService {

    @Resource
    private ShopTrolleyRepository shopTrolleyRepository;

    /**
     * 购物车商品查询
     * @param shopTrolley  用户id
     * @return
     */
    @Override
    public List<ShopTrolley> ShopTrolleyByUserId(ShopTrolley shopTrolley) {
        List<ShopTrolley> shopTrolleys = shopTrolleyRepository.findAll(new Specification<ShopTrolley>() {
            @Override
            public Predicate toPredicate(Root<ShopTrolley> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (shopTrolley != null) {
                    if (shopTrolley.getUserid().getId() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("userid"), shopTrolley.getUserid().getId()));
                    }
                    if (shopTrolley.getBuy() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("buy"), shopTrolley.getBuy()));
                    }
                }
                return predicate;
            }
        });
        return shopTrolleys;
    }

    /**
     * 购物车商品添加
     * @param shopTrolley   商品信息
     * @return
     */
    @Override
    public Integer appendShopToShopTrolley(ShopTrolley shopTrolley) {
        shopTrolleyRepository.save(shopTrolley);
        return null;
    }

    /**
     * 删除购物车商品通过id
     * @param id    购物车id
     */
    @Override
    public void removeShopTrolleyById(Integer id) {
        shopTrolleyRepository.deleteById(id);
    }

    /**
     * 已购买和购物车数量
     * @param shopTrolley
     * @return
     */
    @Override
    public Long alreadyBuyTotal(ShopTrolley shopTrolley) {
        return shopTrolleyRepository.count(new Specification<ShopTrolley>() {
            @Override
            public Predicate toPredicate(Root<ShopTrolley> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (shopTrolley != null) {
                    if (shopTrolley.getUserid() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("userid"), shopTrolley.getUserid()));
                    }
                    if (shopTrolley.getBuy() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("buy"), shopTrolley.getBuy()));
                    }
                }
                return predicate;
            }
        });
    }

    /**
     * 通过id查询单个实体类
     * @param id
     * @return
     */
    @Override
    public ShopTrolley findOneShopTrolley(Integer id) {
        return shopTrolleyRepository.getOne(id);
    }

    /**
     * 通过商家id查询商家的购买订单
     * @param id
     * @return
     */
    @Override
    public List<ShopTrolley> shopSoldByMerChantId(Integer id) {
        return shopTrolleyRepository.findAll(new Specification<ShopTrolley>() {
            @Override
            public Predicate toPredicate(Root<ShopTrolley> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (id != null) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("merchant"), id));
                }
                return predicate;
            }
        });
    }

}
