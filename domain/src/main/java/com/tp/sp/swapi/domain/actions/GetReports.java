package com.tp.sp.swapi.domain.actions;

import com.tp.sp.swapi.domain.ReportQueryRepository;
import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class GetReports {

  private final ReportQueryRepository reportQueryRepository;

  public Mono<Report> getById(int id) {
    return reportQueryRepository.findById(id);
  }

  public Flux<Report> getAll() {
    return reportQueryRepository.findAll();
  }
}
