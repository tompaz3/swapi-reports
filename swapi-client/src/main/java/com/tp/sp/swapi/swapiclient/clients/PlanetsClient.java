package com.tp.sp.swapi.swapiclient.clients;

import com.tp.sp.swapi.swapi.jsonschema.Planets;
import reactor.core.publisher.Mono;

public interface PlanetsClient {

  Mono<Planets> findByName(String name);
}
