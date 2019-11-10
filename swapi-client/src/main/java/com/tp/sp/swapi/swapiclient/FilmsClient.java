package com.tp.sp.swapi.swapiclient;

import static com.tp.sp.swapi.swapiclient.IdFromUrl.toId;

import com.tp.sp.swapi.swapi.jsonschema.Film;
import com.tp.sp.swapi.swapi.jsonschema.Films;
import com.tp.sp.swapi.swapiclient.page.FilmsPageable;
import com.tp.sp.swapi.swapiclient.page.FindAllPages;
import java.util.Collection;
import java.util.function.Predicate;
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
  public Mono<Films> findAllByIds(Collection<Integer> ids) {
    val uri = SwapiUriBuilder.of(getPeopleUri).build();
    return findAllPages(uri, filterByIds(ids));
  }

  private Mono<Films> findAllPages(String uri, Predicate<Film> filter) {
    return findAllPages.findAllPages(
        swapiGetMethodClient.get(uri, Films.class)
            .map(f -> FilmsPageable.of(f, filter))
    );
  }

  private Predicate<Film> filterByIds(Collection<Integer> ids) {
    return f -> ids.contains(toId(f.getUrl()));
  }

}
