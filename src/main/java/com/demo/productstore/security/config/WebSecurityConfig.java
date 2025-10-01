package com.demo.productstore.security.config;

import com.demo.productstore.security.UserAuthority;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Web security configuration class.
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfig {

    /**
     * Configures the security filter chain.
     *
     * @param http the HttpSecurity to configure
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/actuator/**", "/h2-console/**").permitAll()
                        .requestMatchers("/api/v1/product/**").hasAnyRole(
                                UserAuthority.AUTHENTICATED_USER.name(),
                                UserAuthority.USER.name(),
                                UserAuthority.ADMIN.name()
                        )
                )
                .httpBasic(withDefaults());

        return http.build();
    }

}

