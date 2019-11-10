package com.tp.sp.swapi.swapiclient.clients.http;

import com.tp.sp.swapi.swapi.jsonschema.People;
import com.tp.sp.swapi.swapiclient.SwapiGetMethodClient;
import com.tp.sp.swapi.swapiclient.SwapiUriBuilder;
import com.tp.sp.swapi.swapiclient.clients.PeopleClient;
import com.tp.sp.swapi.swapiclient.page.FindAllPages;
import com.tp.sp.swapi.swapiclient.page.PeoplePageable;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PeopleHttpClient implements PeopleClient {

  private static final String SEARCH_BY_NAME_QUERY_PARAM = "search";

  private final FindAllPages<People> findAllPages;
  private final SwapiGetMethodClient swapiGetMethodClient;
  private final String getPeopleUri;

  /**
   * Finds people by given name parameter.
   *
   * @param name name.
   * @return people found.
   */
  @Override
  public Mono<People> findByName(String name) {
    val uri = SwapiUriBuilder.of(getPeopleUri)
        .queryParam(SEARCH_BY_NAME_QUERY_PARAM, name)
        .build();
    return findAllPages(uri);
  }

  private Mono<People> findAllPages(String uri) {
    return findAllPages.findAllPages(
        swapiGetMethodClient.get(uri, People.class).map(PeoplePageable::new)
    );
  }

}
