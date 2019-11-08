package com.tp.sp.swapi.app.report.find;

import com.tp.sp.swapi.app.report.find.mapping.FilmMapper;
import com.tp.sp.swapi.domain.FindAllFilms;
import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.swapi.jsonschema.Films;
import com.tp.sp.swapi.swapiclient.FilmsClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindAllFilmsSwapi implements FindAllFilms {

  private final FilmsClient filmsClient;
  private final FilmMapper filmMapper;

  @Override
  public Flux<Film> findAll() {
    return Flux.from(filmsClient.findAll())
        .map(Films::getResults)
        .flatMap(r -> Flux.fromStream(r.stream()))
        .map(filmMapper::toFilm);
  }
}
