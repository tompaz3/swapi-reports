package com.tp.sp.swapi.app.report.api;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.fasterxml.jackson.core.type.TypeReference;
import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Report;
import com.tp.sp.swapi.api.reports.Reports;
import com.tp.sp.swapi.app.report.RestAssuredTest;
import java.util.List;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.annotation.DirtiesContext;

class MultipleReportResourceApiTest extends RestAssuredTest {

  private static final String REPORT_URI = "/v2/report";

  @SneakyThrows
  @DisplayName("given: 11 records in reports db, "
      + "when: get all reports, "
      + "then expected reports returned")
  @Test
  void givenDataInDbWhenGetAllReportsThenExpectedReportsReturned() {
    val jsonResponse = given()
        .accept(JSON)
        .when()
        .get(REPORT_URI)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .extract().asString();

    val response = objectMapper.readValue(jsonResponse, new TypeReference<List<Reports>>() {
    });

    // then response has 5 reports
    assertThat(response).hasSize(5);
  }

  @SneakyThrows
  @DisplayName("given: records in reports db, "
      + "when: get report by existing reportId, "
      + "then: report returned")
  @Test
  void givenDataInDbWhenGetExistingReportThenReportReturned() {
    val reportId = 1;
    val jsonResponse = given()
        .accept(JSON)
        .when()
        .get(REPORT_URI + "/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .extract().asString();

    val response = objectMapper.readValue(jsonResponse, Reports.class);

    // then report has 7 entries
    assertThat(response.getEntries()).hasSize(7);
    // and all reports has valid id
    assertThat(
        response.getEntries().stream().map(Report::getReportId).allMatch(id -> id == reportId))
        .isTrue();
  }

  @SneakyThrows
  @DisplayName("given: valid query criteria and reportId, "
      + "when: put, "
      + "then: expected report created")
  @Test
  void givenValidQueryCriteriaWhenPutReportThenReportCreated() {
    val reportId = 6;
    val queryCriteria = new QueryCriteria().withQueryCriteriaCharacterPhrase("darth")
        .withQueryCriteriaPlanetName("tatooine");
    val jsonResponse = given()
        .contentType(JSON)
        .accept(JSON)
        .body(queryCriteria)
        .when()
        .put(REPORT_URI + "/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .extract().asString();

    val response = objectMapper.readValue(jsonResponse, Reports.class);

    // then generated more than 1 entry
    assertThat(response.getEntries()).hasSizeGreaterThan(1);
    // and all entries have valid id
    assertThat(response.getEntries().stream().map(Report::getReportId)
        .allMatch(id -> reportId == id)).isTrue();
    // and all entries have the same criteria
    // and contain criteria strings in planet names and character names
    assertThat(response.getEntries().stream()
        .allMatch(r -> hasDataAsInQueryCriteria(r, queryCriteria)))
        .isTrue();
  }

  @SneakyThrows
  @DisplayName("given: valid query criteria and existing report, "
      + "when: create new report for the same id, "
      + "then: old report is overwritten")
  @Test
  void givenValidQueryCriteriaAndExistingReportIdWhenPutNewThenReportOverwritten() {
    // given existing report
    val reportId = 7;
    val queryCriteria = new QueryCriteria().withQueryCriteriaCharacterPhrase("maul")
        .withQueryCriteriaPlanetName("dathomir");
    given()
        .contentType(JSON)
        .accept(JSON)
        .body(queryCriteria)
        .when()
        .put(REPORT_URI + "/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value());

    // when put new report for existing id
    val queryCriteriaOverwrite = new QueryCriteria().withQueryCriteriaCharacterPhrase("darth")
        .withQueryCriteriaPlanetName("tatooine");
    given()
        .contentType(JSON)
        .accept(JSON)
        .body(queryCriteriaOverwrite)
        .when()
        .put(REPORT_URI + "/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON);

    // then get by id returned overwritten report
    val jsonResponse = given()
        .accept(JSON)
        .when()
        .get(REPORT_URI + "/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .extract().asString();

    val response = objectMapper.readValue(jsonResponse, Reports.class);

    // then generated more than 1 entry
    assertThat(response.getEntries()).hasSizeGreaterThan(1);
    // and all entries have valid id
    assertThat(response.getEntries().stream().map(Report::getReportId)
        .allMatch(id -> reportId == id)).isTrue();
    // and all entries have the same criteria
    // and contain criteria strings in planet names and character names
    assertThat(response.getEntries().stream()
        .allMatch(r -> hasDataAsInQueryCriteria(r, queryCriteriaOverwrite)))
        .isTrue();
  }

  @DisplayName("given: report with id, "
      + "when: delete by id, "
      + "then: report deleted")
  @Test
  void givenReportWhenDeleteByIdThenDeleted() {
    // given existing report
    val reportId = 8;
    val queryCriteria = new QueryCriteria().withQueryCriteriaCharacterPhrase("maul")
        .withQueryCriteriaPlanetName("dathomir");
    given()
        .contentType(JSON)
        .accept(JSON)
        .body(queryCriteria)
        .when()
        .put(REPORT_URI + "/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value());

    // when delete by id
    given()
        .when()
        .delete(REPORT_URI + "/" + reportId)
        .then()
        .log().ifError()
        .statusCode(ACCEPTED.value());

    // then record deleted
    given()
        .accept(JSON)
        .when()
        .get(REPORT_URI + "/" + reportId)
        .then()
        .log().ifError()
        .statusCode(NOT_FOUND.value());
  }

  @SneakyThrows
  @DisplayName("given: any reports in db,"
      + "when: delete all, "
      + "then: all deleted")
  @DirtiesContext
  @Test
  void givenReportsWhenDeleteAllThenAllDeleted() {
    // when delete all
    given()
        .delete(REPORT_URI)
        .then()
        .log().ifError()
        .statusCode(ACCEPTED.value());

    // then all deleted
    val reportString = given()
        .accept(JSON)
        .when()
        .get(REPORT_URI)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .extract()
        .body()
        .asString();
    val reports = objectMapper.readValue(reportString, new TypeReference<List<Report>>() {
    });
    assertThat(reports).isEmpty();
  }

  private boolean hasDataAsInQueryCriteria(Report r, QueryCriteria qc) {
    return
        r.getQueryCriteriaCharacterPhrase().equals(qc.getQueryCriteriaCharacterPhrase())
            && r.getQueryCriteriaPlanetName().equals(qc.getQueryCriteriaPlanetName())
            && containsIgnoreCase(r.getCharacterName(), qc.getQueryCriteriaCharacterPhrase())
            && containsIgnoreCase(r.getPlanetName(), qc.getQueryCriteriaPlanetName());
  }
}