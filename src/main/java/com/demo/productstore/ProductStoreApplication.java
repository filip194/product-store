package com.demo.productstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Main application class for the Product Store application.
 */
@SpringBootApplication
@EnableCaching
@EnableScheduling
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
