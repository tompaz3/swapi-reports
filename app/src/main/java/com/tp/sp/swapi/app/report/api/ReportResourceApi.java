package com.tp.sp.swapi.app.report.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Report;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(value = "/report", produces = APPLICATION_JSON_VALUE)
public interface ReportResourceApi {

  @GetMapping
  Flux<Report> getAll();

  @GetMapping(value = "/{reportId}")
  Mono<?> getById(@PathVariable int reportId);

  @PutMapping(value = "/{reportId}", consumes = APPLICATION_JSON_VALUE)
  Mono<?> putReport(@PathVariable int reportId, @RequestBody QueryCriteria queryCriteria);

  @DeleteMapping
  Mono<Void> deleteAll();

  @DeleteMapping("/{reportId}")
  Mono<Void> deleteById(@PathVariable int reportId);
}
