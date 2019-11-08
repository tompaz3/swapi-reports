package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Planet;
import reactor.core.publisher.Flux;

public interface FindPlanetsByName {

  Flux<Planet> findByName(String name);
}
