package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.Comparator;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
class FindPersonAndPlanetByCriteria {

  private final FindPeopleByName findPeopleByName;
  private final FindPlanetsByName findPlanetsByName;

  Mono<Tuple2<Person, Planet>> findByCriteria(QueryCriteria queryCriteria) {
    if (queryCriteria.isEmpty()) {
      return Mono.error(QueryCriteriaEmptyException::new);
    }
    val people = findPeopleByName.findByName(queryCriteria.getCharacterPhrase())
        .filter(this::personHasAnyFilm);
    val planets = findPlanetsByName.findByName(queryCriteria.getPlanetName());
    return zipAndJoin(people, planets).next()
        .map(this::toVavrTuple);
  }

  private boolean personHasAnyFilm(Person person) {
    return !person.getFilmIds().isEmpty();
  }

  private Flux<reactor.util.function.Tuple2<Person, Planet>> zipAndJoin(Flux<Person> people,
      Flux<Planet> planets) {
    return Flux.zip(
        people.sort(Comparator.comparingInt(Person::getHomeWorldId)),
        planets.sort(Comparator.comparingInt(Planet::getId))
    ).filter(t -> t.getT1().getHomeWorldId().equals(t.getT2().getId()));
  }

  private Tuple2<Person, Planet> toVavrTuple(
      reactor.util.function.Tuple2<Person, Planet> reactorTuple) {
    return Tuple.of(reactorTuple.getT1(), reactorTuple.getT2());
  }
}
