package com.kpi.springboot_laba_6.mapper;

import com.kpi.springboot_laba_6.dto.CategoryDTO;
import com.kpi.springboot_laba_6.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDTO categoryToCategoryDTO(Category category);
}
