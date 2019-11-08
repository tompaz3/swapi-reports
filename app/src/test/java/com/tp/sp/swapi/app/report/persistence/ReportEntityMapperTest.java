package com.tp.sp.swapi.app.report.persistence;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.Random;
import java.util.Set;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ReportEntityMapperTest {

  private ReportEntityMapper reportEntityMapper;

  @BeforeEach
  void setUp() {
    reportEntityMapper = new ReportEntityMapper(new ReportSharedEntityMapper());
  }

  @DisplayName("given: report entity and expected report, "
      + "when: map to domain, "
      + "then: mapped object is equal to expected")
  @Test
  void givenReportEntityAndExpectedReportWhenToDomainThenMappedSuccessfully() {
    // given report entity and report objects
    val entityDomainPair = respectiveEntityAndDomainObject();

    // when toDomain called
    val report = reportEntityMapper.toReport(entityDomainPair._1());

    // then mapped is equal to expected
    assertThat(report).isEqualTo(entityDomainPair._2());
  }

  @DisplayName("given: report and expected report entity, "
      + "when: map to entity, "
      + "then: mapped object is equal to expected")
  @Test
  void givenReportAndExpectedReportEntityWhenToEntityThenMappedSuccessfully() {
    // given report entity and report objects
    val entityDomainPair = respectiveEntityAndDomainObject();

    // when toDomain called
    val report = reportEntityMapper.toReportEntity(entityDomainPair._2());

    // then mapped is equal to expected
    assertThat(report).isEqualToComparingFieldByField(entityDomainPair._1());
  }

  private Tuple2<ReportEntity, Report> respectiveEntityAndDomainObject() {
    val random = new Random();
    val reportId = random.nextInt(Integer.MAX_VALUE);
    val criteriaCharacterPhrase = randomAlphanumeric(10);
    val criteriaPlanetName = randomAlphanumeric(10);
    val filmId = random.nextInt();
    val filmName = randomAlphanumeric(10);
    val planetId = random.nextInt();
    val planetName = randomAlphanumeric(10);
    val characterId = random.nextInt();
    val characterName = randomAlphanumeric(10);
    val entity = new ReportEntity();
    entity.setId(reportId);
    entity.setQueryCriteria(new QueryCriteriaEntity(criteriaCharacterPhrase, criteriaPlanetName));
    entity.setFilm(new FilmEntity(filmId, filmName));
    entity.setPlanet(new PlanetEntity(planetId, planetName));
    entity.setPerson(new PersonEntity(characterId, characterName));

    val domain = Report.builder()
        .id(reportId)
        .queryCriteria(QueryCriteria.of(criteriaCharacterPhrase, criteriaPlanetName))
        .film(Film.of(filmId, filmName))
        .planet(Planet.of(planetId, planetName))
        .person(Person.of(characterId, characterName, planetId, Set.of()))
        .build();
    return Tuple.of(entity, domain);
  }
}