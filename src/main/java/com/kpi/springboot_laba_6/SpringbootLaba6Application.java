package com.kpi.springboot_laba_6;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "Online catalogue of goods",
                description = "A platform for accessing information about a diverse range of goods. Explore our catalogue, retrieve product details, and streamline your online shopping experience effortlessly.",
                version = "v1.0",
                contact = @Contact(
                        name="Team-35",
                        email = "team-35@gmail.com"
                )
        )
)
public class SpringbootLaba6Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootLaba6Application.class, args);
    }

}
