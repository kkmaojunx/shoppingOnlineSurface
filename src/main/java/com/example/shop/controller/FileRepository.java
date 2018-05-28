package com.example.shop.controller;

import com.example.shop.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FileRepository extends JpaRepository<File, Integer>, JpaSpecificationExecutor<File> {
}
