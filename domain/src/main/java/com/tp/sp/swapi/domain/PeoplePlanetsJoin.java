package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Stream;
import reactor.core.publisher.Flux;

class PeoplePlanetsJoin {

  private final List<Person> people = new CopyOnWriteArrayList<>();
  private final List<Planet> planets = new CopyOnWriteArrayList<>();
  private final Flux<Person> peopleEmitter;
  private final Flux<Planet> planetsEmitter;

  PeoplePlanetsJoin(Flux<Person> peopleEmitter, Flux<Planet> planetsEmitter) {
    this.peopleEmitter = peopleEmitter;
    this.planetsEmitter = planetsEmitter;
    subscribe();
  }

  private void subscribe() {
    peopleEmitter.subscribe(people::add);
    planetsEmitter.subscribe(planets::add);
  }

  /**
   * Returns all person - planet pairs buffered so far from publishers.
   *
   * <p>Inefficient and memory consuming for huge sets of data emitted by registered publishers.
   *
   * @return person-planet pairs.
   */
  Flux<Tuple2<Person, Planet>> getPeopleFromPlanets() {
    return Flux.fromStream(
        people.stream()
            .flatMap(person -> findPersonsPlanet(person, planets.stream()))
    );
  }

  private Stream<Tuple2<Person, Planet>> findPersonsPlanet(Person person,
      Stream<Planet> planetsStream) {
    return planetsStream.filter(planet -> planet.getId().equals(person.getHomeWorldId()))
        .map(planet -> Tuple.of(person, planet));
  }
}
