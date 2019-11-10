package com.tp.sp.swapi.domain.generate;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.port.FindFilmsByIdsStub;
import com.tp.sp.swapi.domain.port.FindPeopleByNameStub;
import com.tp.sp.swapi.domain.port.FindPersonWithFilmAndPlanetByCriteria;
import com.tp.sp.swapi.domain.port.FindPlanetsByNameStub;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenerateSingleRecordReportTest {

  private GenerateSingleRecordReport generateSingleRecordReport;

  @BeforeEach
  void setUp() {
    val findPersonAndPlanetByCriteria = new FindPersonWithFilmAndPlanetByCriteria(
        new FindPeopleByNameStub(),
        new FindPlanetsByNameStub());
    generateSingleRecordReport = new GenerateSingleRecordReport(findPersonAndPlanetByCriteria,
        new FindFilmsByIdsStub(), new GenerateReportFromTupleMapper());
  }


  @DisplayName("given: criteria for returning planet and person with films, "
      + "when: generate report, "
      + "then: report generated")
  @Test
  void givenCriteriaForFilmsWhenGenerateThenGenerated() {
    // given report id
    val reportId = 1;
    // and query criteria returning planet and person with films
    val queryCriteria = QueryCriteria
        .of(FindPeopleByNameStub.SKYWALKER_NAME, FindPlanetsByNameStub.TATOOINE_NAME);

    // when generate report
    val result = generateSingleRecordReport.generateReport(reportId, queryCriteria);
    val report = result.block();

    // then report generated
    assertThat(report).isNotNull();
    // and report has expected id
    assertThat(report.getId()).isEqualTo(reportId);
    // and report has expected criteria
    assertThat(report.getQueryCriteria()).isEqualTo(queryCriteria);
    // and report contains film
    assertThat(report.getFilm().getId()).isNotNull();
    // and report contains person
    assertThat(report.getPerson().getId()).isNotNull();
    // and person's name contains character phrase
    assertThat(containsIgnoreCase(report.getPerson().getName(), queryCriteria.getCharacterPhrase()))
        .isTrue();
    // and report contains planet
    assertThat(report.getPlanet().getId()).isNotNull();
    // and planet's name contains criteria name
    assertThat(containsIgnoreCase(report.getPlanet().getName(), queryCriteria.getPlanetName()))
        .isTrue();
  }
}