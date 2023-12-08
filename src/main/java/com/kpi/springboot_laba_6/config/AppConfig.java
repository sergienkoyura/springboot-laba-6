package com.kpi.springboot_laba_6.config;

import com.kpi.springboot_laba_6.util.CategoryDB;
import com.kpi.springboot_laba_6.util.ProductDB;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Bean
    public ProductDB productDB(){
        return new ProductDB();
    }

    @Bean
    public CategoryDB categoryDB(){
        return new CategoryDB();
    }

}
