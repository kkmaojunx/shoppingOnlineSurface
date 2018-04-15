package com.example.shop.repository;

import com.example.shop.entity.ShopTrolley;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopTrolleyRepository extends JpaRepository<ShopTrolley, Integer>, JpaSpecificationExecutor<ShopTrolley> {
}
