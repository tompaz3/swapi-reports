package com.tp.sp.swapi;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "com.tp.sp.swapi")
public class StandaloneApplication {

  public static void main(String[] args) {
    new SpringApplicationBuilder(StandaloneApplication.class).run(args);
  }
}
