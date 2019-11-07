package com.tp.sp.swapi.swapiclient;

import com.tp.sp.swapi.swapi.jsonschema.Planets;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PlanetsClient {

  private static final String SEARCH_BY_NAME_QUERY_PARAM = "search";

  private final SwapiGetMethodClient genericFindByNameClient;
  private final String getPlanetsUri;

  /**
   * Finds planets by given name parameter.
   *
   * @param name name.
   * @return planets found.
   */
  public Mono<Planets> findByName(String name) {
    val uri = SwapiUriBuilder.of(getPlanetsUri)
        .queryParam(SEARCH_BY_NAME_QUERY_PARAM, name)
        .build();
    return genericFindByNameClient.get(uri, Planets.class);
  }

}
