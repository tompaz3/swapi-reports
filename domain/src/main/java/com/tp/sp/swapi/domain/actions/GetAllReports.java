package com.tp.sp.swapi.domain.actions;

import com.tp.sp.swapi.domain.ReportQueryRepository;
import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GetAllReports {

  private final ReportQueryRepository reportQueryRepository;

  public Flux<Report> getAll() {
    return reportQueryRepository.findAll();
  }
}
