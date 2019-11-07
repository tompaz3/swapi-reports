package com.tp.sp.swapi.swapiclient;

import com.tp.sp.swapi.swapi.jsonschema.People;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PeopleClient {

  private static final String SEARCH_BY_NAME_QUERY_PARAM = "search";

  private final SwapiGetMethodClient genericFindByNameClient;
  private final String getPeopleUri;

  /**
   * Finds people by given name parameter.
   *
   * @param name name.
   * @return people found.
   */
  public Mono<People> findByName(String name) {
    val uri = SwapiUriBuilder.of(getPeopleUri)
        .queryParam(SEARCH_BY_NAME_QUERY_PARAM, name)
        .build();
    return genericFindByNameClient.get(uri, People.class);
  }
}
