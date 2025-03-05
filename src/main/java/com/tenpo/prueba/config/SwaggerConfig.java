package com.tenpo.prueba.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name}")
    String serverURL;

    @Value("${microservice.initials}")
    String swaggerTitle;

    @Bean
    public OpenAPI springOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Api del proyecto " + swaggerTitle)
                        .description("Aquí podrás encontrar la documentación de endpoints, modelos y DTOs del proyecto")
                        .version("v 0.0.1")
                        .license(new License().name("MIT").url("https://spdx.org/licenses/MIT.html")))
                .addServersItem(new Server().url("https://localhost:8080/" + serverURL));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder().group("Endpoints públicos").pathsToMatch("/api/**").build();
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.tenpo.prueba"))
                .paths(PathSelectors.any())
                .build();
    }
}