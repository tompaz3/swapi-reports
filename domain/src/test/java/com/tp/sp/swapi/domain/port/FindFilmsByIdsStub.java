package com.tp.sp.swapi.domain.port;

import com.tp.sp.swapi.domain.model.Film;
import java.util.Collection;
import java.util.List;
import reactor.core.publisher.Flux;

public class FindFilmsByIdsStub implements FindFilmsByIds {

  private static final List<Film> FILMS = List.of(
      Film.of(1, "A New Hope"),
      Film.of(5, "Attack of the Clones"),
      Film.of(4, "The Phantom Menace"),
      Film.of(6, "Revenge of the Sith"),
      Film.of(3, "Return of the Jedi"),
      Film.of(2, "The Empire Strikes Back"),
      Film.of(7, "The Force Awakens")
  );

  @Override
  public Flux<Film> findAllByIds(Collection<Integer> ids) {
    return Flux.fromStream(FILMS.stream())
        .filter(f -> ids.contains(f.getId()));
  }

}
