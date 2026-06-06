package com.student.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * Main application class.
 * Extends SpringBootServletInitializer for GlassFish WAR deployment.
 */
@SpringBootApplication
public class InventoryApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(InventoryApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(InventoryApplication.class, args);
    }
}
