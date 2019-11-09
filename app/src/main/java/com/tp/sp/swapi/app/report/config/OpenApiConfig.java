package com.tp.sp.swapi.app.report.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  /**
   * Creates base OpenAPI entry.
   *
   * @param docsVersion documentation version.
   * @return base OpenAPI entry.
   */
  @Bean
  public OpenAPI openAPI(@Value("${docs.version:1.0}") String docsVersion) {
    return new OpenAPI()
        .info(new Info().title("Swapi Reports")
            .version(docsVersion)
            .description("This REST API allows you to create, delete and browse reports "
                + "based on https://swapi.co data.")
            .license(new License().name("Apache 2.0")));
  }
}
