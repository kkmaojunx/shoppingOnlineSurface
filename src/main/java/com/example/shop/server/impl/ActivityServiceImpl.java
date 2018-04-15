package com.example.shop.server.impl;

import com.example.shop.entity.Activity;
import com.example.shop.repository.ActivityRepository;
import com.example.shop.server.ActivityService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * 活动Service接口实现类
 */
@Service("activityService")
public class ActivityServiceImpl implements ActivityService {

    @Resource
    private ActivityRepository activityRepository;

    /**
     * 查询所有的活动
     * @return
     */
    @Override
    public List<Activity> activityList(Integer id) {
        List<Activity> activities = activityRepository.findAll(new Specification<Activity>() {
            @Override
            public Predicate toPredicate(Root<Activity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (id != null) {
                    predicate.getExpressions().add(criteriaBuilder.equal(root.get("id"), id));
                }
                return predicate;
            }
        });
        return activities;
    }
}
