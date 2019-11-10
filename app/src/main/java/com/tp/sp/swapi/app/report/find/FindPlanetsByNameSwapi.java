package com.tp.sp.swapi.app.report.find;

import com.tp.sp.swapi.app.report.find.mapping.PlanetMapper;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.port.FindPlanetsByName;
import com.tp.sp.swapi.swapi.jsonschema.Planets;
import com.tp.sp.swapi.swapiclient.clients.PlanetsClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindPlanetsByNameSwapi implements FindPlanetsByName {

  private final PlanetsClient planetsClient;
  private final PlanetMapper planetMapper;

  @Override
  public Flux<Planet> findByName(String name) {
    return Flux.from(planetsClient.findByName(name))
        .map(Planets::getResults)
        .flatMap(r -> Flux.fromStream(r.stream()))
        .map(planetMapper::toPlanet);
  }
}
