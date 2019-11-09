package com.tp.sp.swapi.domain.actions;

import com.tp.sp.swapi.domain.ReportQueryExistsRepository;
import com.tp.sp.swapi.domain.ReportRepository;
import com.tp.sp.swapi.domain.generate.GenerateReport;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class PutManyReports {

  private final GenerateReport<Flux<Report>> generateManyReports;
  private final ReportQueryExistsRepository reportQueryExistsRepository;
  private final ReportRepository reportRepository;

  /**
   * Puts many {@link Report} instances created for given report id and criteria.
   *
   * @param reportId report id.
   * @param queryCriteria query criteria.
   * @return created report instances.
   */
  public Flux<Report> putReports(int reportId, QueryCriteria queryCriteria) {
    return reportQueryExistsRepository.findExistsById(reportId)
        .flatMap(exists -> deleteIfExists(exists, reportId))
        .flatMapMany(id -> generateManyReports.generateReport(id, queryCriteria))
        .flatMap(this::saveReport);
  }

  private Flux<Report> saveReport(Report report) {
    return Flux.from(reportRepository.create(report));
  }

  private Mono<Integer> deleteIfExists(boolean exists, int reportId) {
    return exists
        ? reportRepository.deleteById(reportId).thenReturn(reportId)
        : Mono.just(reportId);
  }
}
