package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.generate.GenerateAllPersonPlanetPairFilmsReport;
import com.tp.sp.swapi.domain.generate.GenerateReport;
import com.tp.sp.swapi.domain.generate.GenerateReportFromTupleMapper;
import com.tp.sp.swapi.domain.generate.GenerateSinglePairAllFilmsReport;
import com.tp.sp.swapi.domain.generate.GenerateSingleRecordReport;
import com.tp.sp.swapi.domain.model.Report;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GenerateReportFactory {

  private final FindPersonWithFilmAndPlanetByCriteria findPersonWithFilmAndPlanetByCriteria;
  private final FindAllFilms findAllFilms;
  private final GenerateReportFromTupleMapper generateReportFromTupleMapper =
      new GenerateReportFromTupleMapper();

  /**
   * Creates new Generate Report factory.
   */
  public GenerateReportFactory(FindPeopleByName findPeopleByName,
      FindPlanetsByName findPlanetsByName, FindAllFilms findAllFilms) {
    this.findPersonWithFilmAndPlanetByCriteria =
        new FindPersonWithFilmAndPlanetByCriteria(findPeopleByName, findPlanetsByName);
    this.findAllFilms = findAllFilms;
  }

  public GenerateReport<Flux<Report>> allPeoplePlanetPairsAllFilms() {
    return new GenerateAllPersonPlanetPairFilmsReport(findPersonWithFilmAndPlanetByCriteria,
        findAllFilms, generateReportFromTupleMapper);
  }

  public GenerateReport<Flux<Report>> personPlanetPairAllFilms() {
    return new GenerateSinglePairAllFilmsReport(findPersonWithFilmAndPlanetByCriteria, findAllFilms,
        generateReportFromTupleMapper);
  }

  public GenerateReport<Mono<Report>> personPlanetFilm() {
    return new GenerateSingleRecordReport(findPersonWithFilmAndPlanetByCriteria, findAllFilms,
        generateReportFromTupleMapper);
  }
}
