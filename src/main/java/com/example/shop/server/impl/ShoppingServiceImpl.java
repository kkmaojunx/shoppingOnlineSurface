package com.example.shop.server.impl;

import com.example.shop.entity.Shopping;
import com.example.shop.repository.ShoppingRepository;
import com.example.shop.server.ShoppingService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 商品service接口实现类
 */
@Service("shoppingService")
public class ShoppingServiceImpl implements ShoppingService {

    @Resource
    private ShoppingRepository shoppingRepository;

    /**
     * 查询商品通过商品id
     *
     * @param id 查询商品的id
     * @return
     */
    @Override
    public Shopping ShoppingById(Integer id) {
        return shoppingRepository.getOne(id);
    }

    /**
     * 指定条件查询商品
     *
     * @param shopping hot Integer 1 or 0
     * @return
     */
    @Override
    public List<Shopping> activityShoppingList(Shopping shopping) {
        List<Shopping> shoppingList = shoppingRepository.findAll(new Specification<Shopping>() {
            @Override
            public Predicate toPredicate(Root<Shopping> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (shopping != null) {
                    if (shopping.getActivity() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("activity"), shopping.getActivity()));
                    }
                }
                return predicate;
            }
        });
        return shoppingList;
    }

    /**
     * 搜索框搜索商品
     * 模糊搜索
     *
     * @param shopping title   String
     * @return
     */
    @Override
    public List<Shopping> searchShoppingList(Shopping shopping) {
        List<Shopping> shoppingList = shoppingRepository.findAll(new Specification<Shopping>() {
            @Override
            public Predicate toPredicate(Root<Shopping> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (shopping != null) {
                    if (shopping.getTitle() != null && !"".equals(shopping.getTitle()) && !"null".equals(shopping.getTitle())) {
                        predicate.getExpressions().add(criteriaBuilder.like(root.get("title"), "%" + shopping.getTitle() + "%"));
                    }
                    if (shopping.getHot() != null && !"".equals(shopping.getHot()) && !"null".equals(shopping.getHot())) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("hot"), shopping.getHot()));
                    }
                }
                return predicate;
            }
        });
        return shoppingList;
    }

    /**
     * 删除商品通过id
     * @param id    商品id
     */
    @Override
    public Integer deleteShoppingById(Integer id) {
        try {
            shoppingRepository.deleteById(id);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 修改or新增商品
     * @param shopping
     */
    @Override
    public void saveShopping(Shopping shopping) {

    }
}
