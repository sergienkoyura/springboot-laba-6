package com.kpi.springboot_laba_6.service.impl;

import com.kpi.springboot_laba_6.dto.ProductDTO;
import com.kpi.springboot_laba_6.entity.Product;
import com.kpi.springboot_laba_6.exception.CouldNotCreateException;
import com.kpi.springboot_laba_6.exception.CouldNotDeleteException;
import com.kpi.springboot_laba_6.exception.DataFormattingException;
import com.kpi.springboot_laba_6.exception.NotFoundException;
import com.kpi.springboot_laba_6.form.UpdateProductForm;
import com.kpi.springboot_laba_6.infopage.PageInfo;
import com.kpi.springboot_laba_6.mapper.ProductMapper;
import com.kpi.springboot_laba_6.repository.CategoryRepository;
import com.kpi.springboot_laba_6.repository.ProductRepository;
import com.kpi.springboot_laba_6.service.ProductService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public ProductDTO getProductById(int id) {
        Product product = productRepository.customFindById(id).orElseThrow(() -> {
            throw new NotFoundException("Product with id " + id + " not found");
        });
        try {
            ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
            ProductDTO productDTO = productMapper.productToProductDTO(product);
            return productDTO;

        } catch (RuntimeException e) {
            throw new DataFormattingException("Product dto creation failed");
        }
    }

    @Override
    public Boolean deleteProduct(int id) {
        productRepository.customFindById(id).orElseThrow(() -> {
            throw new NotFoundException("Product with id " + id + " not found");
        });
        try {
            productRepository.deleteById(id);
            return productRepository.customFindById(id).isEmpty();
        } catch (RuntimeException e) {
            throw new CouldNotDeleteException("Could not delete product");
        }
    }

    @Override
    public Product createProduct(Product product) {
        categoryRepository.findById(product.getCategoryId()).orElseThrow(() -> {
            throw new NotFoundException("Product with id " + product.getCategoryId() + " not found");
        });
        try {
            return productRepository.save(product);
        } catch (RuntimeException e) {
            throw new CouldNotCreateException("Could not create product");
        }
    }

    @Override
    @Transactional
    public Product updateProduct(int id, UpdateProductForm updateProductForm) {
        Product product = productRepository.customFindById(id).orElseThrow(() -> {
            throw new NotFoundException("Product with id " + id + " not found");
        });
        Integer newCategoryId = updateProductForm.getCategoryId();
        if (newCategoryId != null) {
            categoryRepository.findById(newCategoryId).orElseThrow(() -> {
                throw new NotFoundException("Parent category with provided id not found");
            });
        }
        try {
            product.setName(Optional.ofNullable(updateProductForm.getName()).orElse(product.getName()));
            product.setDescription(Optional.ofNullable(updateProductForm.getDescription()).orElse(product.getDescription()));
            product.setPrice(Optional.ofNullable(updateProductForm.getPrice()).orElse(product.getPrice()));
            product.setCategory(categoryRepository.findById(Optional.ofNullable(updateProductForm.getCategoryId()).orElse(product.getCategoryId())).get());

            return productRepository.save(product);
        } catch (RuntimeException e) {
            throw new CouldNotCreateException("Could not update product");
        }
    }

    @Override
    public PageInfo<ProductDTO> getAllProducts(int page, Double maxPrice) {
        Page<Product> info;
        info = maxPrice != null
                ? productRepository.findAllByMaxPrice(maxPrice, PageRequest.of(page, 2))
                : productRepository.findAll(PageRequest.of(page, 2));
        List<Product> productList = info.getContent();
        if (productList.isEmpty()) {
            throw new NotFoundException("Products not found");
        }
        try {
            ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
            List<ProductDTO> productDTOS = productMapper.productsToProductDTO(productList);
            PageInfo<ProductDTO> dtoPageInfo = new PageInfo<>(productDTOS, info.getTotalPages(), info.getTotalElements(), info.getNumberOfElements());
            return dtoPageInfo;
        } catch (Exception e) {
            throw new DataFormattingException("Dto formation failed");
        }
    }
}
