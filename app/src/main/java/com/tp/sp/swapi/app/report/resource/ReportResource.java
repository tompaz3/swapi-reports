package com.tp.sp.swapi.app.report.resource;

import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Report;
import com.tp.sp.swapi.app.report.api.ReportResourceApi;
import lombok.RequiredArgsConstructor;
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
  public Mono<Report> getById(@PathVariable int reportId) {
    return reportResourceService.getById(reportId);
  }

  @Override
  public Mono<Report> putReport(@PathVariable int reportId,
      @RequestBody QueryCriteria queryCriteria) {
    return reportResourceService.putReport(reportId, queryCriteria);
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
