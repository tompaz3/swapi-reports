package com.tp.sp.swapi.app.report.persistence.multiple;

import com.tp.sp.swapi.app.report.persistence.shared.ReportDeletionException;
import com.tp.sp.swapi.domain.ReportQueryExistsRepository;
import com.tp.sp.swapi.domain.ReportQueryRepository;
import com.tp.sp.swapi.domain.ReportRepository;
import com.tp.sp.swapi.domain.model.Report;
import io.vavr.control.Option;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.support.TransactionTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
public class MultipleReportsJpaRepository implements ReportRepository, ReportQueryExistsRepository,
    ReportQueryRepository<Flux<Report>> {

  private final TransactionTemplate transactionTemplate;
  private final MultipleReportsEntityRepository multipleReportsEntityRepository;
  private final MultipleReportEntityMapper reportEntityMapper;

  @Override
  public Mono<Boolean> findExistsById(int reportId) {
    return Mono.fromSupplier(
        () -> multipleReportsEntityRepository.findFirstByReportId(reportId).isPresent());
  }

  @Override
  public Flux<Report> findById(int reportId) {
    return Flux.fromStream(multipleReportsEntityRepository.findAllByReportId(reportId).stream())
        .map(reportEntityMapper::toReport);
  }

  @Override
  public Flux<Report> findAll() {
    return Flux.fromStream(multipleReportsEntityRepository.findAll().stream())
        .map(reportEntityMapper::toReport);
  }

  @Override
  public Mono<Report> create(Report report) {
    return Mono.fromSupplier(() -> report)
        .map(reportEntityMapper::toMultipleReportEntity)
        .map(r -> transactionTemplate.execute(ts -> multipleReportsEntityRepository.save(r)))
        .map(reportEntityMapper::toReport);
  }

  @Override
  public Mono<Report> overwrite(Report report) {
    return create(report);
  }

  @Override
  public Mono<Void> deleteAll() {
    return Mono.fromRunnable(
        () -> transactionTemplate
            .executeWithoutResult(ts -> multipleReportsEntityRepository.deleteAll())
    );
  }

  @Override
  public Mono<Void> deleteById(int reportId) {
    return Mono.fromRunnable(
        () -> transactionTemplate.executeWithoutResult(ts -> doDeleteById(reportId))
    );
  }

  private void doDeleteById(int reportId) {
    final Option<ReportDeletionException> error = Try
        .run(() -> multipleReportsEntityRepository.deleteAllByReportId(reportId))
        .toEither()
        .fold(ReportDeletionException::of, ignore -> Option.none());
    if (error.isDefined()) {
      throw error.get();
    }
  }
}
