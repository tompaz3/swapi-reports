package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Report;
import reactor.core.publisher.Mono;

public interface ReportRepository {

  Mono<Report> create(Report report);

  Mono<Report> overwrite(Report report);

  Mono<Void> deleteAll();

  Mono<Void> deleteById(int reportId);
}
