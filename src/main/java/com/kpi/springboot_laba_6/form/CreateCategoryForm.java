package com.kpi.springboot_laba_6.form;

import jakarta.validation.constraints.NotNull;

public class CreateCategoryForm {

    @NotNull
    private String name;

    @NotNull
    private Integer parentId;

    public CreateCategoryForm(String name, Integer parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}
