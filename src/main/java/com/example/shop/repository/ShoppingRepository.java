package com.example.shop.repository;

import com.example.shop.entity.Shopping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShoppingRepository extends JpaRepository<Shopping, Integer>, JpaSpecificationExecutor<Shopping> {
}
