package com.tp.sp.swapi.app.report.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "swapi")
public class SwapiProperties {

  private SwapiClientPropertiesWrapper client;

}
