package com.tiv.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@EnableSwagger2WebMvc
public class Knife4jConfig {

    @Bean
    public Docket defaultApi2() {
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                        .title("MOCK-DOUYIN RESTFUL APIS")
                        .description("仿抖音项目接口文档")
                        .termsOfServiceUrl("http://www.mock-douyin.com/")
                        .contact(new Contact("tiv", "http://www.mock-douyin.com/", "tiv@163.com"))
                        .version("1.0")
                        .build())
                //分组名称
                .groupName("2.X")
                .select()
                //指定Controller扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.tiv.api.controller"))
                .paths(PathSelectors.any())
                .build();
        return docket;
    }
}
