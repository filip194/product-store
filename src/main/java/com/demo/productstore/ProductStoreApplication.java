package com.demo.productstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Main application class for the Product Store application.
 */
@SpringBootApplication
@EnableCaching
public class ProductStoreApplication {

    /**
     * The entry point of the application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductStoreApplication.class, args);
    }

}
