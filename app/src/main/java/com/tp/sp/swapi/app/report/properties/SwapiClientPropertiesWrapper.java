package com.tp.sp.swapi.app.report.properties;

import com.tp.sp.swapi.swapiclient.SwapiClientProperties;

public class SwapiClientPropertiesWrapper {

  private SwapiClientProperties swapiClientProperties = SwapiClientProperties.builder()
      .build();

  public SwapiClientProperties get() {
    return swapiClientProperties;
  }

  public void setBaseUrl(String baseUrl) {
    this.swapiClientProperties = this.swapiClientProperties.withBaseUrl(baseUrl);
  }

  public String getBaseUrl() {
    return swapiClientProperties.getBaseUrl();
  }

  public void setPeopleUri(String peopleUri) {
    this.swapiClientProperties = this.swapiClientProperties.withPeopleUri(peopleUri);
  }

  public String getPeopleUri() {
    return swapiClientProperties.getPeopleUri();
  }

  public void setFilmsUri(String filmsUri) {
    this.swapiClientProperties = this.swapiClientProperties.withFilmsUri(filmsUri);
  }

  public String getFilmsUri() {
    return swapiClientProperties.getFilmsUri();
  }

  public void setPlanetsUri(String planetsUri) {
    this.swapiClientProperties = this.swapiClientProperties.withPlanetsUri(planetsUri);
  }

  public String getPlanetsUri() {
    return swapiClientProperties.getPlanetsUri();
  }
}
