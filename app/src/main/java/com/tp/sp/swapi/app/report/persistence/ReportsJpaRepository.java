package com.tp.sp.swapi.app.report.persistence;

import com.tp.sp.swapi.domain.ReportQueryRepository;
import com.tp.sp.swapi.domain.ReportRepository;
import com.tp.sp.swapi.domain.model.Report;
import io.vavr.control.Option;
import io.vavr.control.Try;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class ReportsJpaRepository implements ReportRepository, ReportQueryRepository {

  private final ReportEntityRepository reportEntityRepository;
  private final ReportEntityMapper reportEntityMapper;

  @Override
  public Mono<Report> findById(int reportId) {
    return Mono.fromSupplier(() -> reportEntityRepository.findById(reportId))
        .filter(Optional::isPresent)
        .map(Optional::get)
        .map(reportEntityMapper::toReport);
  }

  @Override
  public Flux<Report> findAll() {
    return Flux.fromStream(reportEntityRepository.findAll().stream())
        .map(reportEntityMapper::toReport);
  }

  @Override
  public Mono<Report> create(Report report) {
    return Mono.fromSupplier(() -> report)
        .map(reportEntityMapper::toReportEntity)
        .map(reportEntityRepository::save)
        .map(reportEntityMapper::toReport);
  }

  @Override
  public Mono<Report> overwrite(Report report) {
    return create(report);
  }

  @Override
  public Mono<Void> deleteAll() {
    return Mono.fromRunnable(reportEntityRepository::deleteAll);
  }

  @Override
  public Mono<Void> deleteById(int reportId) {
    return Mono.fromRunnable(() -> doDeleteById(reportId));
  }

  private void doDeleteById(int reportId) {
    final Option<ReportDeletionException> error = Try
        .run(() -> reportEntityRepository.deleteById(reportId))
        .toEither()
        .fold(ReportDeletionException::of, ignore -> Option.none());
    if (error.isDefined()) {
      throw error.get();
    }
  }
}
