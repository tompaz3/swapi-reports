package com.tp.sp.swapi.domain.port;

import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.List;
import java.util.stream.Stream;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

class PeoplePlanetsJoin {

  private final Mono<reactor.util.function.Tuple2<List<Person>, List<Planet>>> peoplePlanetsEmitter;

  /**
   * Publishers need to be fully buffered before any joins.
   *
   * @param peopleEmitter people emitter.
   * @param planetsEmitter planets emitter.
   */
  PeoplePlanetsJoin(Flux<Person> peopleEmitter, Flux<Planet> planetsEmitter) {
    this.peoplePlanetsEmitter = Mono.zip(
        peopleEmitter.collectList(),
        planetsEmitter.collectList()
    );
  }

  /**
   * Returns all person - planet pairs buffered so far from publishers.
   *
   * <p>Inefficient and memory consuming for huge sets of data emitted by registered publishers.
   *
   * @return person-planet pairs.
   */
  Flux<Tuple2<Person, Planet>> getPeopleFromPlanets() {
    return peoplePlanetsEmitter
        .flatMapMany(t2 -> joinPeoplePlanets(t2.getT1(), t2.getT2()));
  }


  private Flux<Tuple2<Person, Planet>> joinPeoplePlanets(List<Person> people,
      List<Planet> planets) {
    return Flux.fromStream(
        people.stream().flatMap(person -> findPersonsPlanet(person, planets.stream()))
    );
  }

  private Stream<Tuple2<Person, Planet>> findPersonsPlanet(Person person,
      Stream<Planet> planetsStream) {
    return planetsStream.filter(planet -> planet.getId().equals(person.getHomeWorldId()))
        .map(planet -> Tuple.of(person, planet));
  }
}
