package com.kpi.springboot_laba_6.util;

import com.kpi.springboot_laba_6.entity.Product;

import java.util.HashMap;

public class ProductDB {
    private int uniqueId;
    private HashMap<Integer, Product> productDB;

    public ProductDB(){
        uniqueId = 1;
        productDB = new HashMap<>();
    }

    public int getUniqueId(){
        return uniqueId;
    }

    public int createUniqueId(){
        return uniqueId++;
    }

    public HashMap<Integer, Product> getProductDB() {
        return productDB;
    }

    public void setProductDB(HashMap<Integer, Product> productDB) {
        this.productDB = productDB;
    }
}
