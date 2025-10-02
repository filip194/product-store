package com.demo.productstore;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "test")
@SpringBootApplication(scanBasePackages = "com.demo.productstore")
public class TestApplication {

}
