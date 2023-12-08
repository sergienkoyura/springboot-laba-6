package com.kpi.springboot_laba_6.mapper;

import com.kpi.springboot_laba_6.dto.SubCategoryInfoDTO;
import com.kpi.springboot_laba_6.entity.Category;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface SubCategoryMapper {
    SubCategoryInfoDTO subCategoryToSubCategoryDTOMapper(Category category);
    default List<SubCategoryInfoDTO> subCategoriesToSubCategoriesDTOMapper(List<Category> categories) {
        List<SubCategoryInfoDTO> subCategoryInfoDTOList = new ArrayList<>();
        for (Category category : categories) {
            SubCategoryInfoDTO subCategoryInfoDTO = subCategoryToSubCategoryDTOMapper(category);
            subCategoryInfoDTOList.add(subCategoryInfoDTO);
        }
        return subCategoryInfoDTOList;
    }
}
