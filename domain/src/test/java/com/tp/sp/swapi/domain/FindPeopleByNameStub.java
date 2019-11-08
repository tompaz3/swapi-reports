package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Person;
import io.vavr.control.Option;
import java.util.List;
import java.util.Map;
import java.util.Set;
import reactor.core.publisher.Flux;

class FindPeopleByNameStub implements FindPeopleByName {

  static final String SKYWALKER_NAME = "skywalker";
  static final String DIFFERENT_PLANET_PEOPLE_NAME = "different_person";
  static final String NO_FILM_PERSON_NAME = "no_film_person";
  static final String NO_PEOPLE_NAME = "no_people";

  private static final Map<String, List<Person>> PEOPLE = Map.ofEntries(
      Map.entry(SKYWALKER_NAME, List.of(
          Person.of(1, "Luke Skywalker", 1, Set.of(1, 2, 3, 5, 6, 7)),
          Person.of(11, "Anakin Skywalker", 1, Set.of(4, 5, 6)),
          Person.of(43, "Shmi Skywalker", 1, Set.of(4, 5)))
      ),
      Map.entry(DIFFERENT_PLANET_PEOPLE_NAME, List.of(
          Person.of(12345, "Different Person", 123, Set.of(1, 2, 3))
      )),
      Map.entry(NO_FILM_PERSON_NAME, List.of(
          Person.of(12346, "No Film Person", 1, Set.of())
      ))
  );


  @Override
  public Flux<Person> findByName(String name) {
    return Option.of(PEOPLE.get(name))
        .map(List::stream)
        .map(Flux::fromStream)
        .getOrElse(Flux::empty);
  }
}
