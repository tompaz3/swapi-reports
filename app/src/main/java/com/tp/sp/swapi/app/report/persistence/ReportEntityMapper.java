package com.tp.sp.swapi.app.report.persistence;

import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportEntityMapper {

  private final ReportSharedEntityMapper reportSharedEntityMapper;

  Report toReport(ReportEntity reportEntity) {
    return reportSharedEntityMapper.toReport(reportEntity.getId(), reportEntity.getQueryCriteria(),
        reportEntity.getPlanet(), reportEntity.getFilm(), reportEntity.getPerson());
  }

  ReportEntity toReportEntity(Report report) {
    val reportEntity = new ReportEntity();
    reportEntity.setId(report.getId());
    reportEntity.setQueryCriteria(
        new QueryCriteriaEntity(report.getQueryCriteria().getCharacterPhrase(),
            report.getQueryCriteria().getPlanetName()));
    reportEntity.setFilm(new FilmEntity(report.getFilm().getId(), report.getFilm().getName()));
    reportEntity
        .setPlanet(new PlanetEntity(report.getPlanet().getId(), report.getPlanet().getName()));
    reportEntity
        .setPerson(new PersonEntity(report.getPerson().getId(), report.getPerson().getName()));
    return reportEntity;
  }
}
