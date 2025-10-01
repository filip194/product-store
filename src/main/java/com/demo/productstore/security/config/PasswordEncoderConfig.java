package com.demo.productstore.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration for password encoding using BCrypt.
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Creates a PasswordEncoder bean that uses BCrypt with a strength of 12.
     *
     * @return a PasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y, 12, null);
    }

}

