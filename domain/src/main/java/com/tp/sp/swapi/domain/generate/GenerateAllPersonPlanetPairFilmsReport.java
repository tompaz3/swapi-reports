package com.tp.sp.swapi.domain.generate;

import com.tp.sp.swapi.domain.FindAllFilms;
import com.tp.sp.swapi.domain.FindPersonWithFilmAndPlanetByCriteria;
import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GenerateAllPersonPlanetPairFilmsReport implements GenerateReport<Flux<Report>> {

  private final FindPersonWithFilmAndPlanetByCriteria findPersonWithFilmAndPlanetByCriteria;
  private final FindAllFilms findAllFilms;
  private final GenerateReportFromTupleMapper generateReportFromTupleMapper;

  /**
   * Generates report base ond given query criteria and report id.
   *
   * <p>Query criteria consist of {@code planet name} and {@code character phrase}.
   * Character must have his home world from the given planet.
   *
   * <p>If no planet - character pair is
   * found, report is not generated.
   *
   * <p>If more than one planet - character pair matches the query, {@link Report} instances are
   * generated for every pair.
   *
   * <p>If person has more than one film, all films found are mapped and multiple {@link Report}
   * instances are generated.
   *
   * @param reportId report id.
   * @param queryCriteria query criteria ({@code planet name} and {@code character phrase}).
   * @return generated {@link Report} instances or empty Flux.
   */
  @Override
  public Flux<Report> generateReport(int reportId, QueryCriteria queryCriteria) {
    return findPersonWithFilmAndPlanetByCriteria.findByCriteria(queryCriteria)
        .flatMap(this::findFilms)
        .map(t3 -> generateReportFromTupleMapper.toReport(reportId, queryCriteria, t3));
  }

  private Flux<Tuple3<Person, Planet, Film>> findFilms(Tuple2<Person, Planet> personPlanet) {
    return findAllFilms.findAll()
        .filter(film -> personPlanet._1().getFilmIds().contains(film.getId()))
        .map(film -> Tuple.of(personPlanet._1(), personPlanet._2(), film));
  }
}
