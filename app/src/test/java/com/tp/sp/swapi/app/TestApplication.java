package com.tp.sp.swapi.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
class TestApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(TestApplication.class).run(args);
  }
}