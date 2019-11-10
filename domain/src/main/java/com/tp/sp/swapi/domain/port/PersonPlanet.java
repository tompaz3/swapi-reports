package com.tp.sp.swapi.domain.port;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor(access = PRIVATE)
public class PersonPlanet {

  @Getter(PACKAGE)
  private final Person person;
  @Getter(PACKAGE)
  private final Planet planet;
  private final Set<Integer> filmIds;

  public Set<Integer> getFilmIds() {
    return person.getFilmIds();
  }

  public Option<Tuple3<Person, Planet, Film>> joinFilmAndRemove(Film film) {
    val removed = !filmIds.isEmpty() && filmIds.remove(film.getId());
    return removed ? Option.of(Tuple.of(person, planet, film)) : Option.none();
  }

  static PersonPlanet fromPersonPlanet(Tuple2<Person, Planet> personPlanet) {
    return new PersonPlanet(personPlanet._1(), personPlanet._2(),
        new HashSet<>(personPlanet._1().getFilmIds()));
  }
}
