package com.tp.sp.swapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "com.tp.sp.swapi")
public class WebApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(WebApplication.class).run(args);
  }
}
