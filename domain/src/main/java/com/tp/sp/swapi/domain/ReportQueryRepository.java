package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Report;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;

public interface ReportQueryRepository<T extends Publisher<Report>> {

  T findById(int reportId);

  Flux<Report> findAll();
}
