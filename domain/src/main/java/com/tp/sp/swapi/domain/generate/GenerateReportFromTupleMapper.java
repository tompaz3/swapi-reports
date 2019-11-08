package com.tp.sp.swapi.domain.generate;

import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import io.vavr.Tuple3;

public class GenerateReportFromTupleMapper {

  Report toReport(int reportId, QueryCriteria queryCriteria,
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
