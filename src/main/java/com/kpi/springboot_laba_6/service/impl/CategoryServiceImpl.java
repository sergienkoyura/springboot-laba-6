package com.kpi.springboot_laba_6.service.impl;

import com.kpi.springboot_laba_6.dto.CategoryDTO;
import com.kpi.springboot_laba_6.dto.ProductDTO;
import com.kpi.springboot_laba_6.dto.SubCategoryInfoDTO;
import com.kpi.springboot_laba_6.entity.Category;
import com.kpi.springboot_laba_6.entity.Product;
import com.kpi.springboot_laba_6.exception.CouldNotCreateException;
import com.kpi.springboot_laba_6.exception.CouldNotDeleteException;
import com.kpi.springboot_laba_6.exception.DataFormattingException;
import com.kpi.springboot_laba_6.exception.NotFoundException;
import com.kpi.springboot_laba_6.infopage.PageInfo;
import com.kpi.springboot_laba_6.mapper.CategoryMapper;
import com.kpi.springboot_laba_6.mapper.ProductMapper;
import com.kpi.springboot_laba_6.mapper.SubCategoryMapper;
import com.kpi.springboot_laba_6.repository.CategoryRepository;
import com.kpi.springboot_laba_6.repository.ProductRepository;
import com.kpi.springboot_laba_6.service.CategoryService;
import jakarta.transaction.Transactional;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public CategoryDTO getCategoryDTOById(int id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Category " + id + " is not found");
        });
        try {
            //Map category
            CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
            CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
            categoryDTO = getSubCategoriesAndProducts(categoryDTO);
            return categoryDTO;
        } catch (RuntimeException e) {
            throw new DataFormattingException("Category dto creation failed");
        }
    }

    @Override
    public Boolean deleteCategory(int id) {
        categoryRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Category " + id + " is not found");
        });
        try {
            categoryRepository.deleteById(id);
            return categoryRepository.findById(id).isEmpty();
        } catch (RuntimeException e) {
            throw new CouldNotDeleteException("Could not delete category");
        }
    }

    @Override
    public Category createCategory(Category category) {
        if (category.getParentId() != 0) {
            categoryRepository.findById(category.getParentId()).orElseThrow(() -> {
                throw new NotFoundException("Category " + category.getParentId() + " is not found");
            });
        }
        try {
            return categoryRepository.save(category);
        } catch (RuntimeException e) {
            throw new CouldNotCreateException("Could not create category");
        }

    }

    @Override
    @Transactional
    public Category updateCategoryName(int id, String name) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Category " + id + " is not found");
        });
        try {
            category.setName(name);
            return categoryRepository.save(category);
        } catch (RuntimeException e) {
            throw new CouldNotCreateException("Could not update category");
        }
    }

    @Override
    public PageInfo<CategoryDTO> getAllCategories(int page, Integer parentId) {
        Page<Category> info;
        info = parentId != null
                ? categoryRepository.findAllByParentId(PageRequest.of(page, 2), parentId)
                : categoryRepository.findAll(PageRequest.of(page, 2));
        List<Category> categories = info.getContent();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        if (categories.isEmpty()) {
            throw new NotFoundException("Categories not found");
        }
        try {
            for (Category category : categories) {
                CategoryMapper categoryMapper = Mappers.getMapper(CategoryMapper.class);
                CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);
                categoryDTO = getSubCategoriesAndProducts(categoryDTO);
                categoryDTOList.add(categoryDTO);
            }
            PageInfo<CategoryDTO> dtoPageInfo = new PageInfo<>(categoryDTOList, info.getTotalPages(), info.getTotalElements(), info.getNumberOfElements());
            return dtoPageInfo;
        } catch (RuntimeException e) {
            throw new DataFormattingException("Could not create the dto");
        }

    }

    private CategoryDTO getSubCategoriesAndProducts(CategoryDTO categoryDTO) throws RuntimeException {
        List<Category> subCategories = categoryRepository.findAllByParentId(categoryDTO.getId());
        List<Product> products = productRepository.findAllByCategoryId(categoryDTO.getId());
        //Map sub-categories
        if (!subCategories.isEmpty()) {
            SubCategoryMapper subCategoryMapper = Mappers.getMapper(SubCategoryMapper.class);
            List<SubCategoryInfoDTO> subCategoryInfoDTOS = subCategoryMapper.subCategoriesToSubCategoriesDTOMapper(subCategories);
            categoryDTO.setSubCategories(subCategoryInfoDTOS);
        }

        //Map products
        if (!products.isEmpty()) {
            ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);
            List<ProductDTO> productDTOS = productMapper.productsToProductDTO(products);
            categoryDTO.setProducts(productDTOS);
        }
        return categoryDTO;

    }
}
