package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Report;
import reactor.core.publisher.Mono;

public interface ReportRepository {

  Mono<Report> create(Report report);

  Mono<Report> overwrite(Report report);

  void deleteAll();

  void deleteById(int reportId);
}
