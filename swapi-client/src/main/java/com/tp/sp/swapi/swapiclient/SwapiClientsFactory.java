package com.tp.sp.swapi.swapiclient;

public class SwapiClientsFactory {

  private final SwapiClientProperties swapiClientProperties;
  private final SwapiGenericFindByNameClient genericFindByNameClient;

  public SwapiClientsFactory(SwapiClientProperties swapiClientProperties,
      SwapiResponseMapper responseMapper) {
    this.swapiClientProperties = swapiClientProperties;
    this.genericFindByNameClient = new SwapiGenericFindByNameClient(
        swapiClientProperties.getBaseUrl(), responseMapper);
  }

  public PeopleClient createPeopleClient() {
    return new PeopleClient(genericFindByNameClient, swapiClientProperties.getPeopleUri());
  }
}
