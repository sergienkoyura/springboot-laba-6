package com.kpi.springboot_laba_6.dto;

import java.util.List;

public class CategoryDTO {
    private int id;

    private String name;

    private List<SubCategoryInfoDTO> subCategories;

    private List<ProductDTO> products;

    public CategoryDTO(int id, String name, List<SubCategoryInfoDTO> subCategories, List<ProductDTO> products) {
        this.id = id;
        this.name = name;
        this.subCategories = subCategories;
        this.products = products;
    }

    public CategoryDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubCategoryInfoDTO> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategoryInfoDTO> subCategories) {
        this.subCategories = subCategories;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
