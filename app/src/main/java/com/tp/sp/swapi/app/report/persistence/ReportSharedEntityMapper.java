package com.tp.sp.swapi.app.report.persistence;

import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ReportSharedEntityMapper {

  Report toReport(int reportId, QueryCriteriaEntity queryCriteria, PlanetEntity planet,
      FilmEntity film,
      PersonEntity person) {
    return Report.builder()
        .id(reportId)
        .queryCriteria(
            QueryCriteria.of(queryCriteria.getCharacterPhrase(), queryCriteria.getPlanetName()))
        .film(Film.of(film.getId(), film.getName()))
        .planet(Planet.of(planet.getId(), planet.getName()))
        .person(Person.of(person.getId(), person.getName(), planet.getId(), Set.of()))
        .build();
  }
}
