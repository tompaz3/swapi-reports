package com.tp.sp.swapi.swapiclient.clients.http;

import com.tp.sp.swapi.swapi.jsonschema.Planets;
import com.tp.sp.swapi.swapiclient.SwapiGetMethodClient;
import com.tp.sp.swapi.swapiclient.SwapiUriBuilder;
import com.tp.sp.swapi.swapiclient.clients.PlanetsClient;
import com.tp.sp.swapi.swapiclient.page.FindAllPages;
import com.tp.sp.swapi.swapiclient.page.PlanetsPageable;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PlanetsHttpClient implements PlanetsClient {

  private static final String SEARCH_BY_NAME_QUERY_PARAM = "search";

  private final FindAllPages<Planets> findAllPages;
  private final SwapiGetMethodClient swapiGetMethodClient;
  private final String getPlanetsUri;

  /**
   * Finds planets by given name parameter.
   *
   * @param name name.
   * @return planets found.
   */
  @Override
  public Mono<Planets> findByName(String name) {
    val uri = SwapiUriBuilder.of(getPlanetsUri)
        .queryParam(SEARCH_BY_NAME_QUERY_PARAM, name)
        .build();
    return findAllPages(uri);
  }

  private Mono<Planets> findAllPages(String uri) {
    return findAllPages.findAllPages(
        swapiGetMethodClient.get(uri, Planets.class).map(PlanetsPageable::new)
    );
  }

}
