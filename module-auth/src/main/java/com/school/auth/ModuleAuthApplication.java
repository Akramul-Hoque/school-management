package com.school.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.school")
public class ModuleAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModuleAuthApplication.class, args);
    }
}
