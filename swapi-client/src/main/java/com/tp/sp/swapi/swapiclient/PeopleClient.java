package com.tp.sp.swapi.swapiclient;

import com.tp.sp.swapi.swapi.jsonschema.People;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PeopleClient {

  private final SwapiGetMethodClient genericFindByNameClient;
  private final String getPeopleUri;

  public Mono<People> findByName(String name) {
    return genericFindByNameClient.get(getPeopleUri, People.class);
  }
}