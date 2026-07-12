package com.kartik.securescan.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()

                .info(new Info()

                        .title("SecureScan API")

                        .version("1.0")

                        .description("Web Security Header & Vulnerability Analyzer"))

                .addSecurityItem(
                        new SecurityRequirement()
                                .addList("Bearer Authentication"))

                .schemaRequirement(
                        "Bearer Authentication",

                        new SecurityScheme()

                                .name("Authorization")

                                .type(SecurityScheme.Type.HTTP)

                                .scheme("bearer")

                                .bearerFormat("JWT"));

    }

}