package com.example.shop.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * 物流地址
 */
@Entity
@Table(name = "object_flow_address")
public class ObjectFlowAddress {

    @Id
    @GeneratedValue
    private Integer id;
    @ManyToOne(cascade = {CascadeType.PERSIST},fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotEmpty(message = "用户id不为空")
    private User userId;        // 用户id
    @Column(length = 255)
    @NotEmpty(message = "收获地址不为空")
    private String address;     // 用户的收获地址
    @Column(length = 11)
    @NotEmpty(message = "电话号码不为空")
    private Long phone;         // 电话号码

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }
}
