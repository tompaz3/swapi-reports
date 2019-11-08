package com.tp.sp.swapi.swapiclient;

import com.tp.sp.swapi.swapi.jsonschema.Films;
import com.tp.sp.swapi.swapiclient.page.FilmsPageable;
import com.tp.sp.swapi.swapiclient.page.FindAllPages;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FilmsClient {

  private final FindAllPages<Films> findAllPages;
  private final SwapiGetMethodClient swapiGetMethodClient;
  private final String getPeopleUri;

  /**
   * Finds all films.
   *
   * @return all films.
   */
  public Mono<Films> findAll() {
    val uri = SwapiUriBuilder.of(getPeopleUri).build();
    return findAllPages(uri);
  }

  private Mono<Films> findAllPages(String uri) {
    return findAllPages.findAllPages(
        swapiGetMethodClient.get(uri, Films.class).map(FilmsPageable::new)
    );
  }

}
