package com.tp.sp.swapi.swapiclient;

import com.tp.sp.swapi.swapi.jsonschema.Films;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FilmsClient {

  private final SwapiGetMethodClient swapiGetMethodClient;
  private final String getPeopleUri;

  /**
   * Finds all films.
   *
   * @return all films.
   */
  public Mono<Films> findAll() {
    val uri = SwapiUriBuilder.of(getPeopleUri).build();
    return swapiGetMethodClient.get(uri, Films.class);
  }
}
