package com.tp.sp.swapi.domain;

import reactor.core.publisher.Mono;

public interface ReportQueryExistsRepository {

  Mono<Boolean> findExistsById(int reportId);
}
