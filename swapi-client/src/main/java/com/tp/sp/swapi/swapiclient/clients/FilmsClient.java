package com.tp.sp.swapi.swapiclient.clients;

import com.tp.sp.swapi.swapi.jsonschema.Films;
import java.util.Collection;
import reactor.core.publisher.Mono;

public interface FilmsClient {

  Mono<Films> findAllByIds(Collection<Integer> ids);
}
