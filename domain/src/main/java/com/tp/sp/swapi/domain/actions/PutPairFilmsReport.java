package com.tp.sp.swapi.domain.actions;

import com.tp.sp.swapi.domain.ReportQueryRepository;
import com.tp.sp.swapi.domain.ReportRepository;
import com.tp.sp.swapi.domain.generate.GenerateReport;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class PutPairFilmsReport {

  private final GenerateReport<Flux<Report>> generatePairFilmsReport;
  private final ReportQueryRepository reportQueryRepository;
  private final ReportRepository reportRepository;

  public Flux<Report> putReport(int reportId, QueryCriteria queryCriteria) {
    return generatePairFilmsReport.generateReport(reportId, queryCriteria)
        .flatMap(this::saveReport);
  }

  private Flux<Report> saveReport(Report report) {
    return Flux.from(
        reportQueryRepository.findById(report.getId())
            .flatMap(r -> reportRepository.overwrite(report))
            .switchIfEmpty(reportRepository.create(report))
    );
  }
}
