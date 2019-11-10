package com.tp.sp.swapi.swapiclient.clients.cached;

import static com.tp.sp.swapi.swapiclient.IdFromUrl.toId;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.tp.sp.swapi.swapi.jsonschema.Film;
import com.tp.sp.swapi.swapi.jsonschema.Films;
import com.tp.sp.swapi.swapiclient.clients.FilmsClient;
import com.tp.sp.swapi.swapiclient.clients.http.FilmsHttpClient;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class FilmsCacheClient implements FilmsClient {

  private volatile String filmsEtag = EMPTY;
  private final Map<Integer, Film> filmsCache = new ConcurrentHashMap<>();

  private final String getPeopleUri;
  private final FilmsHttpClient filmsClient;
  private final SwapiEtagClient swapiEtagClient;

  @Override
  public Mono<Films> findAllByIds(Collection<Integer> ids) {
    return swapiEtagClient.getEtag(getPeopleUri)
        .flatMap(etag -> getOrUpdateAndGet(etag, ids));
  }

  private Mono<Films> getOrUpdateAndGet(String etag, Collection<Integer> ids) {
    if (filmsEtag.equals(etag)) {
      return getAndUpdateIfAnyMissing(filmsCache, ids);
    }
    return updateAndGet(etag, ids);
  }

  private Mono<Films> getAndUpdateIfAnyMissing(Map<Integer, Film> films, Collection<Integer> ids) {
    return filterByIds(films, ids)
        .flatMap(f -> updateAndGetIfMissing(f, ids));
  }

  private Mono<Films> updateAndGet(String etag, Collection<Integer> ids) {
    synchronized (this) {
      filmsEtag = etag;
    }
    filmsCache.clear();
    return filmsClient.findAllByIds(ids)
        .map(f -> updateCache(f.getResults()))
        .flatMap(f -> filterByIds(f, ids));
  }

  private Map<Integer, Film> updateCache(List<Film> films) {
    films.forEach(f -> filmsCache.put(toId(f.getUrl()), f));
    return filmsCache;
  }

  private Mono<Films> filterByIds(Map<Integer, Film> films, Collection<Integer> ids) {
    return Mono.just(ids)
        .map(i -> i.stream().map(films::get)
            .filter(Objects::nonNull)
            .collect(Collectors.toList()))
        .map(new Films()::withResults);
  }

  private Mono<Films> updateAndGetIfMissing(Films films, Collection<Integer> ids) {
    if (films.getResults().size() == ids.size()) {
      return Mono.just(films);
    }
    val searchIds = new ArrayList<>(ids);
    searchIds.removeAll(
        films.getResults().stream().map(f -> toId(f.getUrl())).collect(Collectors.toList()));
    return filmsClient.findAllByIds(searchIds)
        .map(f -> updateCache(f.getResults()))
        .flatMap(f -> filterByIds(f, ids));
  }
}
