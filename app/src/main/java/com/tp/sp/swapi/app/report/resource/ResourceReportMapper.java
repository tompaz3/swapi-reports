package com.tp.sp.swapi.app.report.resource;

import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Report;
import org.springframework.stereotype.Component;

@Component
public class ResourceReportMapper {

  Report toResourceReport(com.tp.sp.swapi.domain.model.Report report) {
    return new Report().withReportId(report.getId())
        .withQueryCriteriaCharacterPhrase(report.getQueryCriteria().getCharacterPhrase())
        .withQueryCriteriaPlanetName(report.getQueryCriteria().getPlanetName())
        .withPlanetId(report.getPlanet().getId())
        .withPlanetName(report.getPlanet().getName())
        .withFilmId(report.getFilm().getId())
        .withFilmName(report.getFilm().getName())
        .withCharacterId(report.getPerson().getId())
        .withCharacterName(report.getPerson().getName());
  }

  com.tp.sp.swapi.domain.model.QueryCriteria fromQueryCriteriaResource(
      QueryCriteria queryCriteria) {
    return com.tp.sp.swapi.domain.model.QueryCriteria
        .of(queryCriteria.getQueryCriteriaCharacterPhrase(),
            queryCriteria.getQueryCriteriaPlanetName());
  }
}
