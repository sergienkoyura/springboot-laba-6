package com.kpi.springboot_laba_6.repository;

import com.kpi.springboot_laba_6.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findAll(Pageable pageable);
    Optional<Product> customFindById(int id);
    @Query("select p from Product p where p.price < ?1")
    Page<Product> findAllByMaxPrice(double maxPrice, Pageable pageable);
    @Query("select p from Product p where p.id = ?1")
    List<Product> findAllByCategoryId(int categoryId);
}
