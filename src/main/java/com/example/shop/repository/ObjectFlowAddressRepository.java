package com.example.shop.repository;

import com.example.shop.entity.ObjectFlowAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 收获地址dao
 */
public interface ObjectFlowAddressRepository extends JpaRepository<ObjectFlowAddress, Integer>, JpaSpecificationExecutor<ObjectFlowAddress> {
}
