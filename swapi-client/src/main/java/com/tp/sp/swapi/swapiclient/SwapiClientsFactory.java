package com.tp.sp.swapi.swapiclient;

import com.tp.sp.swapi.swapiclient.page.FindAllPages;

public class SwapiClientsFactory {

  private final SwapiClientProperties swapiClientProperties;
  private final SwapiGetMethodClient getMethodClient;

  /**
   * Creates new factory instance based on {@link SwapiClientProperties} and {@link
   * SwapiResponseMapper} used for JSON response mappings.
   *
   * <p>Creates {@link SwapiGetMethodClient} used by specific clients implementations to handle
   * HTTP GET requests.
   *
   * @param swapiClientProperties swapi client properties.
   * @param responseMapper response mapper.
   */
  public SwapiClientsFactory(SwapiClientProperties swapiClientProperties,
      SwapiResponseMapper responseMapper) {
    this.swapiClientProperties = swapiClientProperties;
    this.getMethodClient = new SwapiGetMethodClient(swapiClientProperties.getBaseUrl(),
        responseMapper);
  }

  public PeopleClient createPeopleClient() {
    return new PeopleClient(new FindAllPages<>(getMethodClient), getMethodClient,
        swapiClientProperties.getPeopleUri());
  }

  public PlanetsClient createPlanetsClient() {
    return new PlanetsClient(new FindAllPages<>(getMethodClient), getMethodClient,
        swapiClientProperties.getPlanetsUri());
  }

  public FilmsClient createFilmsClient() {
    return new FilmsClient(new FindAllPages<>(getMethodClient), getMethodClient,
        swapiClientProperties.getFilmsUri());
  }
}
