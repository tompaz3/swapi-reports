package com.tp.sp.swapi.app.report.resource;

import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Report;
import com.tp.sp.swapi.domain.actions.DeleteReports;
import com.tp.sp.swapi.domain.actions.GetReports;
import com.tp.sp.swapi.domain.actions.PutReport;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
class ReportResourceService {

  private final DeleteReports deleteReports;
  private final GetReports getReports;
  private final PutReport putReport;
  private final ResourceReportMapper resourceReportMapper;

  public ReportResourceService(@Qualifier("deleteReports") DeleteReports deleteReports,
      GetReports getReports,
      PutReport putReport,
      ResourceReportMapper resourceReportMapper) {
    this.deleteReports = deleteReports;
    this.getReports = getReports;
    this.putReport = putReport;
    this.resourceReportMapper = resourceReportMapper;
  }

  Flux<Report> getAll() {
    return getReports.getAll().map(resourceReportMapper::toResourceReport);
  }


  Mono<Report> getById(int reportId) {
    return getReports.getById(reportId).map(resourceReportMapper::toResourceReport);
  }

  Mono<Report> putReport(int reportId, QueryCriteria queryCriteria) {
    return putReport
        .putReport(reportId, resourceReportMapper.fromQueryCriteriaResource(queryCriteria))
        .map(resourceReportMapper::toResourceReport);
  }

  Mono<Void> deleteAll() {
    return deleteReports.deleteAll();
  }

  Mono<Void> deleteById(int reportId) {
    return deleteReports.deleteById(reportId);
  }

}
