package com.priago.monocleapi.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Configuration for Monocle's Swagger integration
 *
 * @author Stephen Prizio <a href="http://www.saprizio.com">www.saprizio.com</a>
 * @version 1.0
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    public static final String ARTICLE_CONTROLLER = "Articles";
    public static final String AUTHOR_CONTROLLER = "Authors";
    public static final String CATEGORY_CONTROLLER = "Categories";
    public static final String SOURCE_CONTROLLER = "Sources";


    //  METHODS

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.priago.monocleapi.api.controllers")).build()
                .tags(new Tag(ARTICLE_CONTROLLER, "Articles represent written media, often passed as news by reporting outlets/organizations"))
                .tags(new Tag(AUTHOR_CONTROLLER, "Authors are described as the writers of Articles, typically associated to outlets/organizations but can easily be freelancers"))
                .tags(new Tag(CATEGORY_CONTROLLER, "Categories are various organized subsets of media that Monocle can query. Categories include examples such as Sports, Health, and Business as well as others"))
                .tags(new Tag(SOURCE_CONTROLLER, "Sources represent outlets/organizations that publish Articles as a method of reporting news"))
                // ...
                .apiInfo(apiInfo());
    }


    //  HELPERS

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Monocle API")
                .version("1.0.0")
                .description("<p>Monocle's RESTful API designed for bulk data queries and advanced metrics.</p><p>Developed by <a href=\"http://www.saprizio.com\">Stephen Prizio</a> &amp; <a href=\"\">Paolo Drago</a></p><p>Powered by <a href=\"https://newsapi.org/\">NewsAPI</a>.</p>")
                .build();
    }
}
