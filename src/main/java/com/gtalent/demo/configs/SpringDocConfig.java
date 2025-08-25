package com.gtalent.demo.configs;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(new Info()
                        .title("demo")
                        .description("練習swagger docs")
                        .version("v1.0.1")
                        .license(new License().name("My License"))
                )
                .components(new Components()
                    .addSecuritySchemes("bearerAuth",new SecurityScheme().name("bearerAuth")
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")
                    )
                );
    }
    @Bean
    public GroupedOpenApi groupedOpenApi(){
        return GroupedOpenApi
                .builder()
                .group("public-apis")
                .pathsToMatch("/jwt/**"
                        ,"/v2/users/**"
                        ,"/products/**"
                        ,"/suppliers/**")
                .build();
    }
}
