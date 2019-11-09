package com.tp.sp.swapi.app.report.persistence.single;

import com.tp.sp.swapi.app.report.persistence.shared.ReportSharedEntityMapper;
import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReportEntityMapper {

  private final ReportSharedEntityMapper reportSharedEntityMapper;

  Report toReport(ReportEntity reportEntity) {
    return reportSharedEntityMapper.toReport(reportEntity.getId(), reportEntity.getQueryCriteria(),
        reportEntity.getPlanet(), reportEntity.getFilm(), reportEntity.getPerson());
  }

  ReportEntity toReportEntity(Report report) {
    val reportEntity = new ReportEntity();
    reportEntity.setId(report.getId());
    reportEntity.setQueryCriteria(reportSharedEntityMapper.toQueryCriteria(report));
    reportEntity.setFilm(reportSharedEntityMapper.toFilm(report));
    reportEntity.setPlanet(reportSharedEntityMapper.toPlanet(report));
    reportEntity.setPerson(reportSharedEntityMapper.toPerson(report));
    return reportEntity;
  }
}
