package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.generate.GenerateAllPersonPlanetPairFilmsReport;
import com.tp.sp.swapi.domain.generate.GenerateReport;
import com.tp.sp.swapi.domain.generate.GenerateReportFromTupleMapper;
import com.tp.sp.swapi.domain.generate.GenerateSingleRecordReport;
import com.tp.sp.swapi.domain.model.Report;
import com.tp.sp.swapi.domain.port.FindFilmsByIds;
import com.tp.sp.swapi.domain.port.FindPeopleByName;
import com.tp.sp.swapi.domain.port.FindPersonWithFilmAndPlanetByCriteria;
import com.tp.sp.swapi.domain.port.FindPlanetsByName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class GenerateReportFactory {

  private final FindPersonWithFilmAndPlanetByCriteria findPersonWithFilmAndPlanetByCriteria;
  private final FindFilmsByIds findFilmsByIds;
  private final GenerateReportFromTupleMapper generateReportFromTupleMapper =
      new GenerateReportFromTupleMapper();

  /**
   * Creates new Generate Report factory.
   */
  public GenerateReportFactory(FindPeopleByName findPeopleByName,
      FindPlanetsByName findPlanetsByName, FindFilmsByIds findFilmsByIds) {
    this.findPersonWithFilmAndPlanetByCriteria =
        new FindPersonWithFilmAndPlanetByCriteria(findPeopleByName, findPlanetsByName);
    this.findFilmsByIds = findFilmsByIds;
  }

  public GenerateReport<Flux<Report>> allPeoplePlanetPairsAllFilms() {
    return new GenerateAllPersonPlanetPairFilmsReport(findPersonWithFilmAndPlanetByCriteria,
        findFilmsByIds, generateReportFromTupleMapper);
  }

  public GenerateReport<Mono<Report>> personPlanetFilm() {
    return new GenerateSingleRecordReport(findPersonWithFilmAndPlanetByCriteria, findFilmsByIds,
        generateReportFromTupleMapper);
  }
}
