package com.tp.sp.swapi.domain.generate;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.domain.FindAllFilmsStub;
import com.tp.sp.swapi.domain.FindPeopleByNameStub;
import com.tp.sp.swapi.domain.FindPersonWithFilmAndPlanetByCriteria;
import com.tp.sp.swapi.domain.FindPlanetsByNameStub;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenerateAllPersonPlanetPairFilmsReportTest {

  private GenerateAllPersonPlanetPairFilmsReport generateAllPersonPlanetPairFilmsReport;

  @BeforeEach
  void setUp() {
    val findPersonAndPlanetByCriteria = new FindPersonWithFilmAndPlanetByCriteria(
        new FindPeopleByNameStub(),
        new FindPlanetsByNameStub());
    generateAllPersonPlanetPairFilmsReport = new GenerateAllPersonPlanetPairFilmsReport(
        findPersonAndPlanetByCriteria, new FindAllFilmsStub(), new GenerateReportFromTupleMapper());
  }

  @DisplayName("given: criteria for returning planet and person with films, "
      + "when: generate report, "
      + "then: multiple report instances generated for the every person-planet pair "
      + "with multiple person's films")
  @Test
  void givenCriteriaForFilmsWhenGenerateThenGenerated() {
    // given report id
    val reportId = 1;
    // and query criteria returning planet and person with films
    val queryCriteria = QueryCriteria
        .of(FindPeopleByNameStub.SKYWALKER_NAME, FindPlanetsByNameStub.TATOOINE_NAME);

    // when generate report
    val result = generateAllPersonPlanetPairFilmsReport.generateReport(reportId, queryCriteria);
    val reports = result.collectList().block();

    // then reports are generated
    assertThat(reports).isNotEmpty();
    // and all report instances have given id
    assertThat(reports.stream().allMatch(r -> r.getId() == reportId)).isTrue();
    // and all report instances have person's name containing character phrase
    assertThat(reports.stream().allMatch(
        r -> containsIgnoreCase(r.getPerson().getName(), queryCriteria.getCharacterPhrase())))
        .isTrue();
    // and all report instances has 3 different people
    assertThat(reports.stream().map(Report::getPerson).distinct().count()).isEqualTo(3);
    // and all report instances have planet's name containing criteria name
    assertThat(reports.stream().allMatch(
        r -> containsIgnoreCase(r.getPlanet().getName(), queryCriteria.getPlanetName())))
        .isTrue();
    // and all report instances have the same planet
    assertThat(reports.stream().map(Report::getPlanet).distinct().count()).isEqualTo(1);
    // and all report instances per person have different film
    assertThat(
        toPersonReportMap(reports).entrySet().stream().allMatch(this::allRecordsDifferent))
        .isTrue();
  }

  private boolean allRecordsDifferent(Entry<Person, List<Report>> e) {
    return e.getValue().stream().map(Report::getFilm).distinct().count() == e.getValue()
        .size();
  }

  private Map<Person, List<Report>> toPersonReportMap(List<Report> reports) {
    return reports.stream().collect(Collectors.groupingBy(Report::getPerson, Collectors.toList()));
  }
}