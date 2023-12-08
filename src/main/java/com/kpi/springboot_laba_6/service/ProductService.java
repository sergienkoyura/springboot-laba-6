package com.kpi.springboot_laba_6.service;

import com.kpi.springboot_laba_6.dto.ProductDTO;
import com.kpi.springboot_laba_6.entity.Product;
import com.kpi.springboot_laba_6.form.UpdateProductForm;
import com.kpi.springboot_laba_6.infopage.PageInfo;


public interface ProductService {
    ProductDTO getProductById(int id);
    Boolean deleteProduct(int id);
    Product createProduct(Product product);
    Product updateProduct(int id, UpdateProductForm updateProductForm);
    PageInfo<ProductDTO> getAllProducts(int page, Double maxPrice);
}
