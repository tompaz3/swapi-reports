package com.tp.sp.swapi.domain;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReportQueryRepository {

  Mono<Report> findById(int reportId);

  Flux<Report> findAll();
}
