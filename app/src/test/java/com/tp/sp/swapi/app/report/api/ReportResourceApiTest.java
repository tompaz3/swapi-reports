package com.tp.sp.swapi.app.report.api;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Report;
import com.tp.sp.swapi.app.AppTestTags;
import io.restassured.RestAssured;
import java.util.List;
import lombok.SneakyThrows;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@Tag(AppTestTags.APP_INTEGRATION_TEST)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ReportResourceApiTest {

  @Value("${local.server.port}")
  private int port;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  void setUp() {
    RestAssured.port = port;
  }

  @DisplayName("given: 5 records in reports db, "
      + "when: get all reports, "
      + "then expected reports returned")
  @Test
  void givenDataInDbWhenGetAllReportsThenExpectedReportsReturned() {
    given()
        .accept(JSON)
        .when()
        .get("/report")
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .body("report_id", hasItems(1, 2, 3, 4, 5));
  }

  @DisplayName("given: records in reports db, "
      + "when: get report by existing reportId, "
      + "then: report returned")
  @Test
  void givenDataInDbWhenGetExistingReportThenReportReturned() {
    val reportId = 1;
    given()
        .accept(JSON)
        .when()
        .get("/report/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .body("report_id", equalTo(reportId))
        .body("query_criteria_character_phrase", equalTo("sky"))
        .body("query_criteria_planet_name", equalTo("tat"))
        .body("character_id", equalTo(1))
        .body("character_name", equalTo("Luke Skywalker"))
        .body("planet_id", equalTo(1))
        .body("planet_name", equalTo("Tatooine"))
        .body("film_id", equalTo(2))
        .body("film_name", equalTo("The Empire Strikes Back"))
    ;
  }

  @DisplayName("given: valid query criteria and reportId, "
      + "when: put, "
      + "then: expected report created")
  @Test
  void givenValidQueryCriteriaWhenPutReportThenReportCreated() {
    val reportId = 6;
    val queryCriteria = new QueryCriteria().withQueryCriteriaCharacterPhrase("darth")
        .withQueryCriteriaPlanetName("tatooine");
    given()
        .contentType(JSON)
        .accept(JSON)
        .body(queryCriteria)
        .when()
        .put("/report/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .body("report_id", equalTo(reportId))
        .body("query_criteria_character_phrase",
            equalTo(queryCriteria.getQueryCriteriaCharacterPhrase()))
        .body("query_criteria_planet_name", equalTo(queryCriteria.getQueryCriteriaPlanetName()))
        .body("character_name",
            containsStringIgnoringCase(queryCriteria.getQueryCriteriaCharacterPhrase()))
        .body("planet_name",
            containsStringIgnoringCase(queryCriteria.getQueryCriteriaPlanetName()));
  }

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
        .put("/report/" + reportId)
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
        .put("/report/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .body("report_id", equalTo(reportId))
        .body("query_criteria_character_phrase",
            equalTo(queryCriteriaOverwrite.getQueryCriteriaCharacterPhrase()))
        .body("query_criteria_planet_name",
            equalTo(queryCriteriaOverwrite.getQueryCriteriaPlanetName()))
        .body("character_name",
            containsStringIgnoringCase(queryCriteriaOverwrite.getQueryCriteriaCharacterPhrase()))
        .body("planet_name",
            containsStringIgnoringCase(queryCriteriaOverwrite.getQueryCriteriaPlanetName()));

    // then get by id returned overwritten report
    given()
        .accept(JSON)
        .when()
        .get("/report/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .body("report_id", equalTo(reportId))
        .body("query_criteria_character_phrase",
            equalTo(queryCriteriaOverwrite.getQueryCriteriaCharacterPhrase()))
        .body("query_criteria_planet_name",
            equalTo(queryCriteriaOverwrite.getQueryCriteriaPlanetName()))
        .body("character_name",
            containsStringIgnoringCase(queryCriteriaOverwrite.getQueryCriteriaCharacterPhrase()))
        .body("planet_name",
            containsStringIgnoringCase(queryCriteriaOverwrite.getQueryCriteriaPlanetName()));
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
        .put("/report/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value());

    // when delete by id
    given()
        .when()
        .delete("/report/" + reportId)
        .then()
        .log().ifError()
        .statusCode(OK.value());

    // then record deleted
    given()
        .accept(JSON)
        .when()
        .get("/report/" + reportId)
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
        .delete("/report")
        .then()
        .log().ifError()
        .statusCode(OK.value());

    // then all deleted
    val reportString = given()
        .accept(JSON)
        .when()
        .get("/report")
        .then()
        .log().ifError()
        .statusCode(OK.value())
        .contentType(JSON)
        .extract()
        .body()
        .asString();
    val reports = objectMapper.readValue(reportString, new TypeReference<List<Report>>() {
    });
    Assertions.assertThat(reports).isEmpty();
  }

}