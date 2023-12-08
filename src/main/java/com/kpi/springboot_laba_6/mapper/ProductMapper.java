package com.kpi.springboot_laba_6.mapper;


import com.kpi.springboot_laba_6.dto.ProductDTO;
import com.kpi.springboot_laba_6.entity.Product;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ProductMapper {
    ProductDTO productToProductDTO(Product product);
    default List<ProductDTO> productsToProductDTO(List<Product> products) {
        List<ProductDTO> productDTOs = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = productToProductDTO(product);
            productDTOs.add(productDTO);
        }
        return productDTOs;
    }
}
