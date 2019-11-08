package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindPersonWithFilmAndPlanetByCriteria {

  private final FindPeopleByName findPeopleByName;
  private final FindPlanetsByName findPlanetsByName;

  /**
   * Searches for people whose name contains {@link QueryCriteria#getCharacterPhrase()} phrase
   * (ignoring case), who have any films, and whose home world planet is planet which name contains
   * {@link QueryCriteria#getPlanetName()} phrase.
   *
   * @param queryCriteria query criteria.
   * @return matching person-planet pairs or empty.
   */
  public Flux<Tuple2<Person, Planet>> findByCriteria(QueryCriteria queryCriteria) {
    if (queryCriteria.isEmpty()) {
      return Flux.error(QueryCriteriaEmptyException::new);
    }
    val people = findPeopleByName.findByName(queryCriteria.getCharacterPhrase())
        .filter(this::personHasAnyFilm);
    val planets = findPlanetsByName.findByName(queryCriteria.getPlanetName());
    val peoplePlanetsJoin = new PeoplePlanetsJoin(people, planets);
    return peoplePlanetsJoin.getPeopleFromPlanets();
  }

  private boolean personHasAnyFilm(Person person) {
    return !person.getFilmIds().isEmpty();
  }

  private Flux<Tuple2<Person, Planet>> emmitIfPersonFromPlanet(Person person,
      Flux<Planet> planets) {
    return planets.filter(planet -> planet.getId().equals(person.getHomeWorldId()))
        .map(planet -> Tuple.of(person, planet));
  }
}