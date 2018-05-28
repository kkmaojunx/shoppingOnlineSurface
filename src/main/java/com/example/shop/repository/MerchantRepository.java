package com.example.shop.repository;

import com.example.shop.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 店铺repository
 */
public interface MerchantRepository extends JpaRepository<Merchant, Integer>, JpaSpecificationExecutor<Merchant> {
}
