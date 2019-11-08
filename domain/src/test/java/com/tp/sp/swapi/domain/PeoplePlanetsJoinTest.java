package com.tp.sp.swapi.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import java.util.Set;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

class PeoplePlanetsJoinTest {


  @DisplayName("given: people and planets publishers, "
      + "when: join, "
      + "then: expected pairs joined")
  @Test
  void givenPeopleAndPlanetPublishersWhenJoinThenExpectedPairsJoined() {
    // given people flux
    val people = Flux.fromStream(Stream.of(
        Person.of(1, "1", 1, Set.of()),
        Person.of(2, "2", 1, Set.of()),
        Person.of(3, "3", 1, Set.of()),
        Person.of(4, "4", 3, Set.of()),
        Person.of(5, "5", 2, Set.of())
    ));
    // and planets
    val planets = Flux.fromStream(Stream.of(
        Planet.of(1, "1"),
        Planet.of(2, "2"),
        Planet.of(5, "5")
    ));
    // and initialized people planets join
    val peoplePlanetsJoin = new PeoplePlanetsJoin(people, planets);

    // when get people from planets
    val peopleFromPlanets = peoplePlanetsJoin.getPeopleFromPlanets();
    val peoplePlanets = peopleFromPlanets.collectList().block();

    // then 4 pairs found
    assertThat(peoplePlanets.size()).isEqualTo(4);
  }
}