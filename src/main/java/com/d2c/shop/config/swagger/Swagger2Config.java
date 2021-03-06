package com.d2c.shop.config.swagger;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author BaiCai
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket bApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("B端接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex("/b_api/.*"))
                .build();
    }

    @Bean
    public Docket cApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("C端接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex("/c_api/.*"))
                .build();
    }

    @Bean
    public Docket backApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("后台接口")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .paths(PathSelectors.regex("/back/.*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("API接口文档")
                .description("Api Documentation")
                .version("1.0.0")
                .build();
    }

}
