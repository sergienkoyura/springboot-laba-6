package com.kpi.springboot_laba_6.service;

import com.kpi.springboot_laba_6.dto.CategoryDTO;
import com.kpi.springboot_laba_6.entity.Category;
import com.kpi.springboot_laba_6.infopage.PageInfo;

public interface CategoryService {
    CategoryDTO getCategoryDTOById(int id);
    Boolean deleteCategory(int id);
    Category createCategory(Category category);
    Category updateCategoryName(int id, String name);
    PageInfo<CategoryDTO> getAllCategories(int page, Integer parentId);
}
