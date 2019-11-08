package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GenerateReport {

  private final FindPersonAndPlanetByCriteria findPersonAndPlanetByCriteria;
  private final FindAllFilms findAllFilms;

  /**
   * Generates report based on given query criteria and report id.
   *
   * <p>Query criteria consist of {@code planet name} and {@code character phrase}.
   * Character must have his home world from the given planet.
   *
   * <p>If no planet - character pair is
   * found, report is not generated.
   *
   * <p>If more than one planet - character pair matches the query, first pair found is used for
   * the report. Pairs containing people for whom no film exists, are filtered out.
   *
   * <p>If person has more than one film, first film found is used for the report.
   *
   * @param reportId report id.
   * @param queryCriteria query criteria ({@code planet name} and {@code character phrase}).
   * @return generated report or empty Mono.
   */
  public Mono<Report> generateReport(int reportId, QueryCriteria queryCriteria) {
    return findPersonAndPlanetByCriteria.findByCriteria(queryCriteria)
        .flatMap(this::findFilm)
        .map(t3 -> toReport(reportId, queryCriteria, t3));

  }

  private Mono<Tuple3<Person, Planet, Film>> findFilm(Tuple2<Person, Planet> personPlanet) {
    return findAllFilms.findAll()
        .filter(film -> personPlanet._1().getFilmIds().contains(film.getId()))
        .next()
        .map(film -> Tuple.of(personPlanet._1(), personPlanet._2(), film));
  }

  private Report toReport(int reportId, QueryCriteria queryCriteria,
      Tuple3<Person, Planet, Film> personPlanetFilm) {
    return Report.builder()
        .id(reportId)
        .queryCriteria(queryCriteria)
        .person(personPlanetFilm._1())
        .planet(personPlanetFilm._2())
        .film(personPlanetFilm._3())
        .build();
  }


}
