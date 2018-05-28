package com.example.shop.repository;

import com.example.shop.entity.File;
import com.example.shop.entity.ShopLabel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopLabelRepository extends JpaRepository<ShopLabel, Integer>, JpaSpecificationExecutor<ShopLabel> {
}
