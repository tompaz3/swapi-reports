package com.tp.sp.swapi.app.report.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReportQueryRepository {

  Mono<Report> findById(int reportId);

  Flux<Report> findAll();
}
