package com.tp.sp.swapi.swapiclient.page;

import com.tp.sp.swapi.swapiclient.SwapiGetMethodClient;
import com.tp.sp.swapi.swapiclient.SwapiUriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FindAllPages<T> {

  private final SwapiGetMethodClient swapiGetMethodClient;

  /**
   * Finds all pages for given pageable object.
   *
   * @param firstPage first page mono.
   * @return all data after having scanned all pages.
   */
  public Mono<T> findAllPages(Mono<Pageable<T>> firstPage) {
    return firstPage
        .flatMap(this::appendIfNextExists)
        .map(Pageable::value);
  }

  private Mono<Pageable<T>> appendIfNextExists(Pageable<T> pageable) {
    if (StringUtils.isEmpty(pageable.getNext())) {
      return Mono.just(pageable);
    } else {
      val uri = SwapiUriBuilder.of(pageable.getNext()).build();
      return swapiGetMethodClient.get(uri, pageable.type())
          .map(pageable.type()::cast)
          .map(pageable::newPageable)
          .map(pageable::append)
          .flatMap(this::appendIfNextExists);
    }
  }
}
