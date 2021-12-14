package com.mediscreen.historymicroservice.configuration;

/**
 * Class of Swagger Configuration
 * Swagger is a very handy API documentation tool. It allows to generate “living” documentation,
 * thus allowing the documentation to be always up to date.
 */


/**

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mediscreen.historymicroservice"))
                .paths(PathSelectors.any())
                .build();
    }
}
 */