package com.tp.sp.swapi.domain.generate;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.domain.FindAllFilmsStub;
import com.tp.sp.swapi.domain.FindPeopleByNameStub;
import com.tp.sp.swapi.domain.FindPersonWithFilmAndPlanetByCriteria;
import com.tp.sp.swapi.domain.FindPlanetsByNameStub;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GenerateSinglePairAllFilmsReportTest {

  private GenerateSinglePairAllFilmsReport generateSinglePairAllFilmsReport;

  @BeforeEach
  void setUp() {
    val findPersonAndPlanetByCriteria = new FindPersonWithFilmAndPlanetByCriteria(
        new FindPeopleByNameStub(),
        new FindPlanetsByNameStub());
    generateSinglePairAllFilmsReport = new GenerateSinglePairAllFilmsReport(
        findPersonAndPlanetByCriteria, new FindAllFilmsStub(), new GenerateReportFromTupleMapper());
  }

  @DisplayName("given: criteria for returning planet and person with films, "
      + "when: generate report, "
      + "then: multiple report instances generated for the same person-planet "
      + "with multiple person's films")
  @Test
  void givenCriteriaForFilmsWheneGenerateThenGenerated() {
    // given report id
    val reportId = 1;
    // and query criteria returning planet and person with films
    val queryCriteria = QueryCriteria
        .of(FindPeopleByNameStub.SKYWALKER_NAME, FindPlanetsByNameStub.TATOOINE_NAME);

    // when generate report
    val result = generateSinglePairAllFilmsReport.generateReport(reportId, queryCriteria);
    val reports = result.collectList().block();

    // then reports are generated
    assertThat(reports).isNotEmpty();
    // and all report instances have given id
    assertThat(reports.stream().allMatch(r -> r.getId() == reportId)).isTrue();
    // and all report instances have person's name containing character phrase
    assertThat(reports.stream().allMatch(
        r -> containsIgnoreCase(r.getPerson().getName(), queryCriteria.getCharacterPhrase())))
        .isTrue();
    // and all report instances have the same person
    assertThat(reports.stream().map(Report::getPerson).distinct().count()).isEqualTo(1);
    // and all report instances have planet's name containing criteria name
    assertThat(reports.stream().allMatch(
        r -> containsIgnoreCase(r.getPlanet().getName(), queryCriteria.getPlanetName())))
        .isTrue();
    // and all report instances have the same planet
    assertThat(reports.stream().map(Report::getPlanet).distinct().count()).isEqualTo(1);
    // and all report instances have different film
    assertThat(reports.stream().map(Report::getFilm).distinct().count()).isEqualTo(reports.size());
  }
}