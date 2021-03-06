package com.tp.sp.swapi.domain.port;

import com.tp.sp.swapi.domain.model.Planet;
import io.vavr.control.Option;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Flux;

public class FindPlanetsByNameStub implements FindPlanetsByName {

  public static final String TATOOINE_NAME = "Tatooine";
  public static final String ENDOR_NAME = "Endor";
  public static final String N_NAME = "n";
  public static final String NO_PLANETS_NAME = "no_planets";

  private static final Map<String, List<Planet>> PLANETS = Map.ofEntries(
      Map.entry(TATOOINE_NAME, List.of(Planet.of(1, "Tatooine"))),
      Map.entry(ENDOR_NAME, List.of(Planet.of(7, "Endor"))),
      Map.entry(N_NAME, List.of(Planet.of(1, "Tatooine"), Planet.of(7, "Endor")))
  );

  @Override
  public Flux<Planet> findByName(String name) {
    return Option.of(PLANETS.get(name))
        .map(List::stream)
        .map(Flux::fromStream)
        .getOrElse(Flux::empty);
  }
}
