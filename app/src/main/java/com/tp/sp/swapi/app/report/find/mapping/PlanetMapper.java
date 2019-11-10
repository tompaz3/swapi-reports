package com.tp.sp.swapi.app.report.find.mapping;

import static com.tp.sp.swapi.swapiclient.IdFromUrl.toId;

import com.tp.sp.swapi.domain.model.Planet;

public class PlanetMapper {

  public Planet toPlanet(com.tp.sp.swapi.swapi.jsonschema.Planet planet) {
    return Planet.of(toId(planet.getUrl()), planet.getName());
  }
}
