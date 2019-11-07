package com.tp.sp.swapi.app.report.domain;

import com.tp.sp.swapi.api.reports.Report;
import reactor.core.publisher.Mono;

public interface ReportRepository {

  Mono<Report> create(Report report);

  Mono<Report> overwrite(Report report);

  void deleteAll();

  void deleteById(int reportId);
}
