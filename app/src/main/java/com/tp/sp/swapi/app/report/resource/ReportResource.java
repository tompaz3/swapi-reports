package com.tp.sp.swapi.app.report.resource;

import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Report;
import com.tp.sp.swapi.app.report.api.ReportResourceApi;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class ReportResource implements ReportResourceApi {

  private final ReportResourceService reportResourceService;

  @Override
  public Flux<Report> getAll() {
    return reportResourceService.getAll();
  }

  @Override
  public Mono<?> getById(@PathVariable int reportId) {
    return reportResourceService.getById(reportId)
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
  }

  @Override
  public Mono<?> putReport(@PathVariable int reportId,
      @RequestBody QueryCriteria queryCriteria) {
    return reportResourceService.putReport(reportId, queryCriteria)
        .map(ResponseEntity::ok)
        .switchIfEmpty(Mono.just(ResponseEntity.badRequest().build()));
  }

  @Override
  public Mono<Void> deleteAll() {
    return reportResourceService.deleteAll();
  }

  @Override
  public Mono<Void> deleteById(@PathVariable int reportId) {
    return reportResourceService.deleteById(reportId);
  }
}
