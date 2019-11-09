package com.tp.sp.swapi.app.report.resource;

import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Reports;
import com.tp.sp.swapi.app.report.api.MultipleReportResourceApi;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class MultipleReportResource implements MultipleReportResourceApi {

  private final MultipleReportResourceService multipleReportResourceService;

  @Override
  public ResponseEntity<List<Reports>> getAll() {
    return ResponseEntity.ok(multipleReportResourceService.getAll().block());
  }

  @Override
  public ResponseEntity<Reports> getById(int reportId) {
    return multipleReportResourceService.getById(reportId).blockOptional()
        .map(ResponseEntity::ok)
        .orElseGet(ResponseEntity.notFound()::build);
  }

  @Override
  public ResponseEntity<Reports> putReport(int reportId, QueryCriteria queryCriteria) {
    return multipleReportResourceService.putReport(reportId, queryCriteria).blockOptional()
        .map(ResponseEntity::ok)
        .orElseGet(ResponseEntity.notFound()::build);
  }

  @Override
  public Mono<ResponseEntity> deleteAll() {
    return multipleReportResourceService.deleteAll()
        .thenReturn(ResponseEntity.accepted().build());
  }

  @Override
  public Mono<ResponseEntity> deleteById(int reportId) {
    return multipleReportResourceService.deleteById(reportId)
        .thenReturn(ResponseEntity.accepted().build());
  }
}
