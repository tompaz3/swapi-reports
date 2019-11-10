package com.tp.sp.swapi.swapiclient.clients;

import com.tp.sp.swapi.swapi.jsonschema.People;
import reactor.core.publisher.Mono;

public interface PeopleClient {

  Mono<People> findByName(String name);
}
