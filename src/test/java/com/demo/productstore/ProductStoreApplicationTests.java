package com.demo.productstore;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(value = "h2dev")
@SpringBootTest
class ProductStoreApplicationTests {

    @Test
    void contextLoads() {
    }

}
