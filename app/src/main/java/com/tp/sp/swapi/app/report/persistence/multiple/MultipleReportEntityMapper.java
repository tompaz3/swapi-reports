package com.tp.sp.swapi.app.report.persistence.multiple;

import com.tp.sp.swapi.app.report.persistence.shared.ReportSharedEntityMapper;
import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MultipleReportEntityMapper {

  private final ReportSharedEntityMapper reportSharedEntityMapper;

  Report toReport(MultipleReportsEntity reportsEntity) {
    return reportSharedEntityMapper
        .toReport(reportsEntity.getReportId(), reportsEntity.getQueryCriteria(),
            reportsEntity.getPlanet(), reportsEntity.getFilm(),
            reportsEntity.getPerson());
  }

  MultipleReportsEntity toMultipleReportEntity(Report report) {
    val entity = new MultipleReportsEntity();
    entity.setReportId(report.getId());
    entity.setQueryCriteria(reportSharedEntityMapper.toQueryCriteria(report));
    entity.setFilm(reportSharedEntityMapper.toFilm(report));
    entity.setPlanet(reportSharedEntityMapper.toPlanet(report));
    entity.setPerson(reportSharedEntityMapper.toPerson(report));
    return entity;
  }
}
