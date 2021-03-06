package com.tp.sp.swapi.app.report.config;

import com.tp.sp.swapi.app.report.properties.SwapiProperties;
import com.tp.sp.swapi.app.report.swapi.SwapiHttpClientFactory;
import com.tp.sp.swapi.swapiclient.JsonMapperProvider;
import com.tp.sp.swapi.swapiclient.SwapiClientsFactory;
import com.tp.sp.swapi.swapiclient.SwapiResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwapiClientsConfig {

  @Bean
  public SwapiResponseMapper swapiResponseMapper() {
    return new SwapiResponseMapper(new JsonMapperProvider());
  }

  @Bean
  public SwapiClientsFactory swapiClientsFactory(SwapiHttpClientFactory swapiHttpClientFactory,
      SwapiProperties swapiProperties,
      SwapiResponseMapper swapiResponseMapper) {
    return new SwapiClientsFactory(swapiHttpClientFactory, swapiProperties.getClient().get(),
        swapiResponseMapper);
  }
}
