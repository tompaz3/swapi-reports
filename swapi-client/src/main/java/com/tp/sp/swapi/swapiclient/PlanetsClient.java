package com.tp.sp.swapi.swapiclient;

import com.tp.sp.swapi.swapi.jsonschema.Planets;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PlanetsClient {

  private final SwapiGenericFindByNameClient genericFindByNameClient;
  private final String getPlanetsUri;

  public Mono<Planets> findByName(String name) {
    return genericFindByNameClient.findByName(getPlanetsUri, name, Planets.class);
  }

}
