package com.tp.sp.swapi.domain.actions;

import com.tp.sp.swapi.domain.ReportQueryRepository;
import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetManyReports {

  private final ReportQueryRepository<Flux<Report>> reportQueryRepository;

  public Flux<Report> getById(int id) {
    return reportQueryRepository.findById(id);
  }

  public Flux<Report> getAll() {
    return reportQueryRepository.findAll();
  }
}
