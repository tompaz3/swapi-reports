package com.tp.sp.swapi.app;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootConfiguration
@EnableAutoConfiguration
public class Application {

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class).run(args);
  }
}
