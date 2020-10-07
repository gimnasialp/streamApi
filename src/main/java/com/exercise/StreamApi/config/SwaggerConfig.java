package com.exercise.StreamApi.config;

import com.fasterxml.classmate.TypeResolver;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket swaggerApi() {
    TypeResolver typeResolver = new TypeResolver();
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors
            .basePackage("com.exercise.StreamApi"))
        .paths(PathSelectors.regex("/.*"))
        .build()
        .apiInfo(metaInfo());
  }

  private ApiInfo metaInfo() {
    return new ApiInfo("Stream Api Challenge",
        "Stream Api Challenge by Wenance",
        "1.0",
        "Terms of Service",
        new Contact("Miguel Centellas Leon", "Web",
            "migue.centellas@gmail.com"),
        "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
        Collections.emptyList()
    );

  }

}
