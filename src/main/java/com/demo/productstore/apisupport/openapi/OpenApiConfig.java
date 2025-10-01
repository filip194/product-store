package com.demo.productstore.apisupport.openapi;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

/**
 * Open API 3 Swagger config class.
 */
@Configuration
public class OpenApiConfig {

    public static final String PRODUCTS_GROUP_NAME = "product";

    @Value("${app.release-version}")
    private String appReleaseVersion;

    @Value("${app.release-name}")
    private String appReleaseName;

    /**
     * Grouped Open API bean for product endpoints.
     *
     * @return GroupedOpenApi
     */
    @Bean
    public GroupedOpenApi productApi() {
        return GroupedOpenApi.builder()
                .group(PRODUCTS_GROUP_NAME)
                .pathsToMatch("/api/v1/product/**")
                .build();
    }

    /**
     * Open API bean with application info, security and server components.
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .servers(Collections.singletonList(new Server().url("http://localhost:8080").description("Local server")))
                .components(new Components()
                        .addSecuritySchemes(
                                "basicScheme",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("basic").name("basicScheme")
                        )
                )
                .info(new Info()
                        .title("Product Store API")
                        .description("Spring Boot 3 Product Store application with connection to HNB API for latest currency exchange rates.")
                        .version(appReleaseName + "-" + appReleaseVersion)
                        .license(new License().name("MIT License").url("https://mit-license.org/"))
                );
    }
}
