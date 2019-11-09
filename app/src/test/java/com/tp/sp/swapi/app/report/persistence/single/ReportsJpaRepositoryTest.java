package com.tp.sp.swapi.app.report.persistence.single;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.app.AppTestTags;
import com.tp.sp.swapi.app.report.config.PersistenceConfig;
import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import java.util.Random;
import java.util.Set;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@Tag(AppTestTags.APP_INTEGRATION_TEST)
@ComponentScan(basePackages = "com.tp.sp.swapi.app.report.persistence")
@Import(PersistenceConfig.class)
@DataJpaTest
class ReportsJpaRepositoryTest {

  @Autowired
  private ReportsJpaRepository reportsJpaRepository;

  @BeforeEach
  void setUp() {
    reportsJpaRepository.create(report(1)).block();
    reportsJpaRepository.create(report(2)).block();
    reportsJpaRepository.create(report(3)).block();
  }

  @AfterEach
  void cleanUp() {
    reportsJpaRepository.deleteAll().block();
  }

  @DisplayName("given: report id for existing report, "
      + "when: find by this id, "
      + "then: report is found")
  @Test
  void givenExistingReportIdWhenFindByIdThenReportFound() {
    // given existing report id
    val reportId = 1;

    // when findById
    val reportOpt = reportsJpaRepository.findById(reportId).blockOptional();

    // then report found
    assertThat(reportOpt).isPresent();
  }

  @DisplayName("given: report id for non existing report, "
      + "when: find by this id, "
      + "then: no report is found")
  @Test
  void givenNonExistingReportIdWhenFindByIdThenNoReportFound() {
    // given non existing report id
    val reportId = 123;

    // when findById
    val reportOpt = reportsJpaRepository.findById(reportId).blockOptional();

    // then report not found
    assertThat(reportOpt).isEmpty();
  }

  @DisplayName("given: initialized data, "
      + "when: findAll, "
      + "then: expected number of reports found")
  @Test
  void givenInitializedDataWhenFindAllThenExpectedNumberOfReportsFound() {
    // given initialized data
    // when findAll
    val reports = reportsJpaRepository.findAll();

    // then all reports found
    assertThat(reports.count().block()).isEqualTo(3);
  }

  @DisplayName("given: report with non existing id, "
      + "when: create, "
      + "then: new report created")
  @Test
  void givenReportWithNonExistingIdWhenCreateThenNewReportCreated() {
    // given report with non existing id
    val report = report(555);

    // when create
    val created = reportsJpaRepository.create(report).block();

    // then created new report is equal to given report
    assertThat(created).isEqualTo(report);
    // and report can be found by id
    assertThat(reportsJpaRepository.findById(report.getId()).blockOptional()).isPresent();
  }

  @DisplayName("given: report with existing id, "
      + "when: overwrite, "
      + "then: existing report overwritten with the newly created one")
  @Test
  void givenReportWithExistingIdWhenOverwriteThenReportOverwritten() {
    // given report with existing id
    val report = report(2);

    // when create
    val created = reportsJpaRepository.overwrite(report).block();

    // then overwritten report is equal to given report
    assertThat(created).isEqualTo(report);
    // and found report is equal to created
    assertThat(
        reportsJpaRepository.findById(report.getId()).filter(created::equals).blockOptional())
        .isPresent();
  }

  @DisplayName("given: initialized data, "
      + "when: deleteAll, "
      + "then: all reports deleted")
  @Test
  void givenInitializedDataWhenDeleteAllThenAllReportsDeleted() {
    // given initialized data
    // when deleteAll
    val delete = reportsJpaRepository.deleteAll();
    // and block task finished
    delete.block();

    // then all reports deleted
    val afterDeletionCount = reportsJpaRepository.findAll().count().block();
    assertThat(afterDeletionCount).isEqualTo(0);
  }

  @DisplayName("given: report id for existing report, "
      + "when: deleteById, "
      + "then: report of given id gets deleted")
  @Test
  void givenReportIdForExistingReportWhenDeleteByIdThenReportDeleted() {
    // given report id for existing report
    val reportId = 3;

    // when deleteById
    val delete = reportsJpaRepository.deleteById(reportId);
    // and block task finished
    delete.block();

    // then report deleted
    assertThat(reportsJpaRepository.findById(reportId).blockOptional()).isEmpty();
  }

  @DisplayName("given: report id for non existing report, "
      + "when: deleteById, "
      + "then: no report gets deleted")
  @Test
  void givenReportIdForNonExistingReportWhenDeleteByIdThenNoReportDeleted() {
    // given report id for non existing report
    val reportId = 992;
    // and count before deletion
    val reportsCount = reportsJpaRepository.findAll().count().block();

    // when deleteById
    val delete = reportsJpaRepository.deleteById(reportId);
    // and block task finished
    delete.block();

    // then nothing changed
    assertThat(reportsJpaRepository.findAll().count().block()).isEqualTo(reportsCount);
  }

  private Report report(int id) {
    val random = new Random();
    val planetId = random.nextInt(Integer.MAX_VALUE);
    return Report.builder()
        .id(id)
        .queryCriteria(QueryCriteria.of(randomAlphanumeric(10), randomAlphanumeric(10)))
        .film(Film.of(random.nextInt(Integer.MAX_VALUE), randomAlphanumeric(10)))
        .planet(Planet.of(planetId, randomAlphanumeric(10)))
        .person(Person.of(random.nextInt(Integer.MAX_VALUE), randomAlphanumeric(10), planetId,
            Set.of()))
        .build();
  }
}