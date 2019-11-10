package com.tp.sp.swapi.swapiclient.clients.cached;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.tp.sp.swapi.swapi.jsonschema.Planet;
import com.tp.sp.swapi.swapi.jsonschema.Planets;
import com.tp.sp.swapi.swapiclient.clients.PlanetsClient;
import com.tp.sp.swapi.swapiclient.clients.http.PlanetsHttpClient;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.map.LRUMap;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PlanetsCacheClient implements PlanetsClient {

  private volatile String planetsEtag = EMPTY;
  private final Map<String, List<Planet>> planetsCache = Collections
      .synchronizedMap(new LRUMap<>(100));

  private final String getPlanetsUri;
  private final PlanetsHttpClient planetsClient;
  private final SwapiEtagClient swapiEtagClient;

  @Override
  public Mono<Planets> findByName(String name) {
    return swapiEtagClient.getEtag(getPlanetsUri)
        .flatMap(etag -> getOrUpdateAndGet(etag, name.toLowerCase()));
  }

  private Mono<Planets> getOrUpdateAndGet(String etag, String name) {
    if (planetsEtag.equals(etag)) {
      return getAndUpdateIfAnyMissing(planetsCache, name);
    }
    return updateAndGet(etag, name);
  }

  private Mono<Planets> updateAndGet(String etag, String name) {
    synchronized (this) {
      planetsEtag = etag;
    }
    planetsCache.clear();
    return planetsClient.findByName(name)
        .map(p -> updateCache(p.getResults(), name))
        .flatMap(p -> filterByName(p, name));
  }

  private Map<String, List<Planet>> updateCache(List<Planet> results, String name) {
    planetsCache.put(name, results);
    return planetsCache;
  }

  private Mono<Planets> filterByName(Map<String, List<Planet>> planets, String name) {
    return Mono.just(name)
        .map(n -> planets.get(name))
        .map(new Planets()::withResults);
  }

  private Mono<Planets> getAndUpdateIfAnyMissing(Map<String, List<Planet>> people, String name) {
    if (people.get(name) != null) {
      return filterByName(people, name);
    }
    return planetsClient.findByName(name)
        .map(f -> updateCache(f.getResults(), name))
        .flatMap(f -> filterByName(f, name));
  }
}
