package com.tp.sp.swapi.app.report.api;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Report;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
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

  @Operation(summary = "Retrieves all reports")
  @ApiResponse(responseCode = "200", description = "OK")
  @GetMapping
  Flux<Report> getAll();

  @Operation(summary = "Retrieves report for given report id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Report found"),
      @ApiResponse(responseCode = "404", description = "Report with such report id does not exist")
  })
  @GetMapping(value = "/{reportId}")
  Mono<?> getById(
      @Parameter(description = "Report id", required = true) @PathVariable int reportId);

  @Operation(summary = "Create new report for given report id")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Report successfully created"),
      @ApiResponse(responseCode = "400",
          description = "Report could not be generated based on given criteria")
  })
  @PutMapping(value = "/{reportId}", consumes = APPLICATION_JSON_VALUE)
  Mono<?> putReport(
      @Parameter(description = "Report id", required = true) @PathVariable int reportId,
      @Parameter(description = "Query criteria", required = true)
      @RequestBody QueryCriteria queryCriteria);

  @Operation(summary = "Deletes all reports")
  @ApiResponse(responseCode = "202", description = "Deletion process has been submitted")
  @DeleteMapping
  Mono<ResponseEntity> deleteAll();

  @Operation(summary = "Deletes report for given report id")
  @ApiResponse(responseCode = "202", description = "Deletion process has been submitted")
  @DeleteMapping("/{reportId}")
  Mono<ResponseEntity> deleteById(
      @Parameter(description = "Report id", required = true) @PathVariable int reportId);
}
