package com.kpi.springboot_laba_6.dto;

public class SubCategoryInfoDTO {
    private int id;
    private String name;

    public SubCategoryInfoDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public SubCategoryInfoDTO() {
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
}
