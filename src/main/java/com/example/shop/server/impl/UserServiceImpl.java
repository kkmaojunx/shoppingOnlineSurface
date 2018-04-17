package com.example.shop.server.impl;

import com.example.shop.entity.User;
import com.example.shop.repository.UserRepository;
import com.example.shop.server.UserService;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * 用户信息Service接口实现类
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    /**
     * 用户注册 or 修改
     * @param user
     */
    @Override
    public void registerUser(User user) {
        userRepository.save(user);
    }

    /**
     * 用户登陆
     * @param user
     * @return
     */
    @Override
    public User findUser(User user) {
        Optional<User> userOptional = userRepository.findOne(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if (user != null) {
                    if (user.getUsername() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("username"), user.getUsername()));
                    }
                    if (user.getPassword() != null) {
                        predicate.getExpressions().add(criteriaBuilder.equal(root.get("password"), user.getPassword()));
                    }
                }
                return predicate;
            }
        });
        try {
            return userOptional.get();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除用户
     * @param user
     */
    @Override
    public void deleteUser(User user) {
        userRepository.deleteById(user.getId());
    }
}
