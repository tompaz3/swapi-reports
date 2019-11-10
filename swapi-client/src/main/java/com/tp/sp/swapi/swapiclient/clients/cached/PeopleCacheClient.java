package com.tp.sp.swapi.swapiclient.clients.cached;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.tp.sp.swapi.swapi.jsonschema.People;
import com.tp.sp.swapi.swapi.jsonschema.Person;
import com.tp.sp.swapi.swapiclient.clients.PeopleClient;
import com.tp.sp.swapi.swapiclient.clients.http.PeopleHttpClient;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.map.LRUMap;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PeopleCacheClient implements PeopleClient {

  private volatile String peopleEtag = EMPTY;
  private final Map<String, List<Person>> peopleCache = Collections
      .synchronizedMap(new LRUMap<>(100));

  private final String getPeopleUri;
  private final PeopleHttpClient peopleClient;
  private final SwapiEtagClient swapiEtagClient;

  @Override
  public Mono<People> findByName(String name) {
    return swapiEtagClient.getEtag(getPeopleUri)
        .flatMap(etag -> getOrUpdateAndGet(etag, name.toLowerCase()));
  }

  private Mono<People> getOrUpdateAndGet(String etag, String name) {
    if (peopleEtag.equals(etag)) {
      return getAndUpdateIfAnyMissing(peopleCache, name);
    }
    return updateAndGet(etag, name);
  }

  private Mono<People> updateAndGet(String etag, String name) {
    synchronized (this) {
      peopleEtag = etag;
    }
    peopleCache.clear();
    return peopleClient.findByName(name)
        .map(p -> updateCache(p.getResults(), name))
        .flatMap(p -> filterByName(p, name));
  }

  private Map<String, List<Person>> updateCache(List<Person> results, String name) {
    peopleCache.put(name, results);
    return peopleCache;
  }

  private Mono<People> filterByName(Map<String, List<Person>> people, String name) {
    return Mono.just(name)
        .map(n -> people.get(name))
        .map(new People()::withResults);
  }

  private Mono<People> getAndUpdateIfAnyMissing(Map<String, List<Person>> people, String name) {
    if (people.get(name) != null) {
      return filterByName(people, name);
    }
    return peopleClient.findByName(name)
        .map(f -> updateCache(f.getResults(), name))
        .flatMap(f -> filterByName(f, name));
  }
}
