package com.tp.sp.swapi.domain.actions;

import com.tp.sp.swapi.domain.ReportQueryRepository;
import com.tp.sp.swapi.domain.ReportRepository;
import com.tp.sp.swapi.domain.generate.GenerateReport;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PutSingleReport {

  private final GenerateReport<Mono<Report>> generateSingleReport;
  private final ReportQueryRepository reportQueryRepository;
  private final ReportRepository reportRepository;

  public Mono<Report> putReport(int reportId, QueryCriteria queryCriteria) {
    return generateSingleReport.generateReport(reportId, queryCriteria)
        .flatMap(this::saveReport);
  }

  private Mono<Report> saveReport(Report report) {
    return reportQueryRepository.findById(report.getId())
        .flatMap(r -> reportRepository.overwrite(report))
        .switchIfEmpty(reportRepository.create(report));
  }
}
