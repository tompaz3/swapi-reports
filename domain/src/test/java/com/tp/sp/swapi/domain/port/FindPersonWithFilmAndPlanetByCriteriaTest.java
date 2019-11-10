package com.tp.sp.swapi.domain.port;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FindPersonWithFilmAndPlanetByCriteriaTest {

  private FindPersonWithFilmAndPlanetByCriteria findPersonWithFilmAndPlanetByCriteria;

  @BeforeEach
  void setUp() {
    findPersonWithFilmAndPlanetByCriteria = new FindPersonWithFilmAndPlanetByCriteria(
        new FindPeopleByNameStub(),
        new FindPlanetsByNameStub());
  }

  @DisplayName("given: people whose home world is 1 "
      + "and planets, one of which has id 1, "
      + "when: find, "
      + "then: all person - planet pairs found")
  @Test
  void givenPeopleFromPlanet1AndPlanetsOneOfWhichIs1WhenFindThenAllPairsFound() {
    // given people from home world 1
    val characterPhrase = FindPeopleByNameStub.SKYWALKER_NAME;
    // and planets, one of which has id 1
    val planetName = FindPlanetsByNameStub.N_NAME;
    // and criteria
    val criteria = QueryCriteria.of(characterPhrase, planetName);

    // when find
    val result = findPersonWithFilmAndPlanetByCriteria.findByCriteria(criteria);
    val personPlanets = result.collectList().block();

    // then all records planets have id 1
    assertThat(
        personPlanets.stream().map(PersonPlanet::getPlanet).map(Planet::getId).distinct()
            .reduce((a, b) -> {
              throw new RuntimeException("Different planet ids");
            }).get()).isEqualTo(1);
    // and person name contains character phrase
    assertThat(personPlanets.stream().map(PersonPlanet::getPerson).map(Person::getName)
        .allMatch(n -> containsIgnoreCase(n, FindPeopleByNameStub.SKYWALKER_NAME)))
        .isTrue();
    // and 3 different people found
    assertThat(personPlanets.stream().map(PersonPlanet::getPerson).distinct().count()).isEqualTo(3);
  }

  @DisplayName("given: people whose home world is 1 "
      + "and planets, one of which has id 1, "
      + "when: find, "
      + "then: person - planet pair found")
  @Test
  void givenPeopleFromPlanet1AndPlanetsOneOfWhichIs1WhenFindThenPairFound() {
    // given people from home world 1
    val characterPhrase = FindPeopleByNameStub.SKYWALKER_NAME;
    // and planets, one of which has id 1
    val planetName = FindPlanetsByNameStub.N_NAME;
    // and criteria
    val criteria = QueryCriteria.of(characterPhrase, planetName);

    // when find
    val result = findPersonWithFilmAndPlanetByCriteria.findByCriteria(criteria);
    val personPlanet = result.next().block();

    // then planet has id 1
    assertThat(personPlanet.getPlanet().getId()).isEqualTo(1);
    // and person name contains character phrase
    assertThat(
        containsIgnoreCase(personPlanet.getPerson().getName(), FindPeopleByNameStub.SKYWALKER_NAME))
        .isTrue();
  }

  @DisplayName("given: people whose home world is 1 "
      + "and no planets, "
      + "when: find, "
      + "then: nothing found")
  @Test
  void givenPeopleFromPlanet1AndNoPlanetsWhenFindThenNothingFound() {
    // given people from home world 1
    val characterPhrase = FindPeopleByNameStub.SKYWALKER_NAME;
    // and no planets
    val planetName = FindPlanetsByNameStub.NO_PLANETS_NAME;
    // and criteria
    val criteria = QueryCriteria.of(characterPhrase, planetName);

    // when find
    val result = findPersonWithFilmAndPlanetByCriteria.findByCriteria(criteria);
    val personPlanet = result.next().blockOptional();

    // then nothing is found
    assertThat(personPlanet).isEmpty();
  }

  @DisplayName("given: people whose home world is 1 "
      + "and no matching planet, "
      + "when: find, "
      + "then: nothing found")
  @Test
  void givenPeopleFromPlanet1AndNoMatchingPlanetWhenFindThenNothingFound() {
    // given people from home world 1
    val characterPhrase = FindPeopleByNameStub.SKYWALKER_NAME;
    // and no matching planet
    val planetName = FindPlanetsByNameStub.ENDOR_NAME;
    // and criteria
    val criteria = QueryCriteria.of(characterPhrase, planetName);

    // when find
    val result = findPersonWithFilmAndPlanetByCriteria.findByCriteria(criteria);
    val personPlanet = result.next().blockOptional();

    // then nothing is found
    assertThat(personPlanet).isEmpty();
  }

  @DisplayName("given: no matching people "
      + "and planet with id 1, "
      + "when: find, "
      + "then: nothing found")
  @Test
  void givenNoMathingPeopleAndPlanetId1WhenFindThenNothingFound() {
    // given people from home world 1
    val characterPhrase = FindPeopleByNameStub.DIFFERENT_PLANET_PEOPLE_NAME;
    // and planet with id 1
    val planetName = FindPlanetsByNameStub.TATOOINE_NAME;
    // and criteria
    val criteria = QueryCriteria.of(characterPhrase, planetName);

    // when find
    val result = findPersonWithFilmAndPlanetByCriteria.findByCriteria(criteria);
    val personPlanet = result.next().blockOptional();

    // then nothing is found
    assertThat(personPlanet).isEmpty();
  }

  @DisplayName("given: people from planet 1 with no films "
      + "and planet with id 1, "
      + "when: find, "
      + "then: nothing found")
  @Test
  void givenPersonFromPlanet1WithNoFilmsAndPlanetId1WhenFindThenNothingFound() {
    // given people from home world 1 with no films
    val characterPhrase = FindPeopleByNameStub.NO_FILM_PERSON_NAME;
    // and planet with id 1
    val planetName = FindPlanetsByNameStub.TATOOINE_NAME;
    // and criteria
    val criteria = QueryCriteria.of(characterPhrase, planetName);

    // when find
    val result = findPersonWithFilmAndPlanetByCriteria.findByCriteria(criteria);
    val personPlanet = result.next().blockOptional();

    // then nothing is found
    assertThat(personPlanet).isEmpty();
  }

}