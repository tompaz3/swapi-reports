package com.tp.sp.swapi.app.report.api.v3;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.tp.sp.swapi.api.reports.Report;
import java.util.stream.Stream;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(value = "/v3/report", produces = APPLICATION_JSON_VALUE)
@RestController
public class AllPairAllFilmsReportResource {

  @GetMapping
  Flux<Report> getAll() {
    return Flux.fromStream(
        Stream.of(1, 2, 3, 4, 5)
            .map(i -> new Report()
                .withReportId(i)
                .withCharacterId(i)
                .withCharacterName("Character " + i)
                .withFilmId(i)
                .withFilmName("Film " + i)
                .withPlanetId(i)
                .withPlanetName("Planet " + i)
                .withQueryCriteriaCharacterPhrase("Character " + i)
                .withQueryCriteriaPlanetName("Planet " + i))
    );
  }

  @GetMapping(value = "/{reportId}")
  Mono<Report> getById(@PathVariable int reportId) {
    return Mono.just(new Report()
        .withReportId(reportId)
        .withCharacterId(reportId)
        .withCharacterName("Character " + reportId)
        .withFilmId(reportId)
        .withFilmName("Film " + reportId)
        .withPlanetId(reportId)
        .withPlanetName("Planet " + reportId)
        .withQueryCriteriaCharacterPhrase("Character " + reportId)
        .withQueryCriteriaPlanetName("Planet " + reportId));
  }

  @PutMapping(value = "/{reportId}", consumes = APPLICATION_JSON_VALUE)
  Mono<Report> putReport(@PathVariable int reportId) {
    return Mono.just(new Report()
        .withReportId(reportId)
        .withCharacterId(reportId)
        .withCharacterName("Character " + reportId)
        .withFilmId(reportId)
        .withFilmName("Film " + reportId)
        .withPlanetId(reportId)
        .withPlanetName("Planet " + reportId)
        .withQueryCriteriaCharacterPhrase("Character " + reportId)
        .withQueryCriteriaPlanetName("Planet " + reportId));
  }

  @DeleteMapping
  void deleteAll() {

  }

  @DeleteMapping("/{reportId}")
  void deleteById(@PathVariable int reportId) {

  }
}
