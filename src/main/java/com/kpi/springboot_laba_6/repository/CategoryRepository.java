package com.kpi.springboot_laba_6.repository;

import com.kpi.springboot_laba_6.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Page<Category> findAll(Pageable pageable);
    Page<Category> findAllByParentId(Pageable pageable, int parentId);
    List<Category> findAllByParentId(int id);
}
