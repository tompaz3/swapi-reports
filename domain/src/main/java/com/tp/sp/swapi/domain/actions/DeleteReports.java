package com.tp.sp.swapi.domain.actions;

import com.tp.sp.swapi.domain.ReportRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class DeleteReports {

  private final ReportRepository reportRepository;

  public Mono<Void> deleteById(int id) {
    return reportRepository.deleteById(id);
  }

  public Mono<Void> deleteAll() {
    return reportRepository.deleteAll();
  }
}
