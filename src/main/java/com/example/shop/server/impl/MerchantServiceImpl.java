package com.example.shop.server.impl;

import com.example.shop.entity.Merchant;
import com.example.shop.repository.MerchantRepository;
import com.example.shop.server.MerchantService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * 店铺service实现类
 */
@Service("merchantService")
public class MerchantServiceImpl implements MerchantService {

    @Resource
    private MerchantRepository merchantRepository;

    /**
     * 查询店铺信息
     *
     * @param merchant 用户的id 或者店铺id
     * @return
     */
    @Override
    public Merchant findOneMerchantByUserId(Merchant merchant) {
        if (merchant.getId() != null) {
            merchant = merchantRepository.getOne(merchant.getId());
        } else {
            Merchant finalMerchant = merchant;
            Optional<Merchant> merchant1 = merchantRepository.findOne(new Specification<Merchant>() {
                @Override
                public Predicate toPredicate(Root<Merchant> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    Predicate predicate = criteriaBuilder.conjunction();
                    if (finalMerchant.getUserId() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("userId"), finalMerchant.getUserId().getId()));
                    }
                    return predicate;
                }
            });
            if (merchant1.isPresent()) {
                merchant = merchant1.get();
            } else {
                merchant = null;
            }
        }
        return merchant;
    }

    /**
     * 删除店铺
     *
     * @param id 店铺的id
     */
    @Override
    public void deleteMerchantById(Integer id) {
        merchantRepository.deleteById(id);
    }

    /**
     * 添加或者修改店铺信息
     *
     * @param merchant 店铺信息
     */
    @Override
    public void addMerchant(Merchant merchant) {
        merchantRepository.save(merchant);
    }
}
