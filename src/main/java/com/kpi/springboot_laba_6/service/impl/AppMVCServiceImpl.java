package com.kpi.springboot_laba_6.service.impl;

import com.kpi.springboot_laba_6.entity.Category;
import com.kpi.springboot_laba_6.entity.Product;
import com.kpi.springboot_laba_6.exception.NotFoundException;
import com.kpi.springboot_laba_6.repository.CategoryRepository;
import com.kpi.springboot_laba_6.repository.ProductRepository;
import com.kpi.springboot_laba_6.service.AppMVCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppMVCServiceImpl implements AppMVCService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findCategoryById(int id) {
        return categoryRepository.findById(id).orElseThrow(()->{
            throw new NotFoundException("Category " + id + " is not found");
        });
    }



    @Override
    public void updateCategory(int id, Category category) {
        categoryRepository.save(category);
    }



    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllProductsByCategoryId(int id) {
        return productRepository.findAllByCategoryId(id);
    }

    @Override
    public Product findProductById(int id) {
        return productRepository.customFindById(id).orElseThrow(()->{
            throw new NotFoundException("Product " + id + " is not found");
        });
    }

    @Override
    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    @Override
    public void createProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void updateProduct(int id, Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
    @Override
    public void deleteCategory(int id) {
        categoryRepository.deleteById(id);
    }




}
