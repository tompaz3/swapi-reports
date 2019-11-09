package com.tp.sp.swapi.app.report.persistence.multiple;

import com.tp.sp.swapi.domain.ReportQueryRepository;
import com.tp.sp.swapi.domain.ReportRepository;
import com.tp.sp.swapi.domain.model.Report;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class MultipleReportsJpaRepository implements ReportRepository, ReportQueryRepository {

  private final MultipleReportsEntityRepository multipleReportsEntityRepository;
  private final MultipleReportEntityMapper reportEntityMapper;

  @Override
  public Mono<Report> findById(int reportId) {
    return null;
  }

  @Override
  public Flux<Report> findAll() {
    return null;
  }

  @Override
  public Mono<Report> create(Report report) {
    return null;
  }

  @Override
  public Mono<Report> overwrite(Report report) {
    return null;
  }

  @Override
  public Mono<Void> deleteAll() {
    return null;
  }

  @Override
  public Mono<Void> deleteById(int reportId) {
    return null;
  }
}
