package com.schoolbus;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SchoolBusServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolBusServiceApplication.class, args);
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("School Bus Service API")
                        .description("CRUD API for managing school bus students")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("School Bus Service Team")
                                .email("admin@schoolbus.com")));
    }
} 