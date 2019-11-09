package com.tp.sp.swapi.app.report.persistence.shared;

import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import java.util.Set;
import org.springframework.stereotype.Component;

@Component
public class ReportSharedEntityMapper {

  /**
   * Maps given parameters to a single {@link Report} instance.
   *
   * @param reportId report id.
   * @param queryCriteria query criteria.
   * @param planet planet.
   * @param film film.
   * @param person person.
   * @return mapped {@link Report} instance.
   */
  public Report toReport(int reportId, QueryCriteriaEntity queryCriteria, PlanetEntity planet,
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

  public QueryCriteriaEntity toQueryCriteria(Report report) {
    return new QueryCriteriaEntity(report.getQueryCriteria().getCharacterPhrase(),
        report.getQueryCriteria().getPlanetName());
  }

  public FilmEntity toFilm(Report report) {
    return new FilmEntity(report.getFilm().getId(), report.getFilm().getName());
  }

  public PlanetEntity toPlanet(Report report) {
    return new PlanetEntity(report.getPlanet().getId(), report.getPlanet().getName());
  }

  public PersonEntity toPerson(Report report) {
    return new PersonEntity(report.getPerson().getId(), report.getPerson().getName());
  }
}
