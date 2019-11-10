package com.tp.sp.swapi.domain.generate;

import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import com.tp.sp.swapi.domain.port.FindFilmsByIds;
import com.tp.sp.swapi.domain.port.FindPersonWithFilmAndPlanetByCriteria;
import com.tp.sp.swapi.domain.port.PersonPlanet;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GenerateSingleRecordReport implements GenerateReport<Mono<Report>> {

  private final FindPersonWithFilmAndPlanetByCriteria findPersonWithFilmAndPlanetByCriteria;
  private final FindFilmsByIds findFilmsByIds;
  private final GenerateReportFromTupleMapper generateReportFromTupleMapper;

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
  @Override
  public Mono<Report> generateReport(int reportId, QueryCriteria queryCriteria) {
    return findPersonWithFilmAndPlanetByCriteria.findByCriteria(queryCriteria)
        .next()
        .flatMap(this::findFilm)
        .map(t3 -> generateReportFromTupleMapper.toReport(reportId, queryCriteria, t3));

  }

  private Mono<Tuple3<Person, Planet, Film>> findFilm(PersonPlanet personPlanet) {
    return findFilmsByIds.findAllByIds(personPlanet.getFilmIds())
        .map(personPlanet::joinFilmAndRemove)
        .filter(Option::isDefined)
        .map(Option::get)
        .next();
  }

}
