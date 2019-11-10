package com.tp.sp.swapi.app.report.find;

import com.tp.sp.swapi.app.report.find.mapping.FilmMapper;
import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.port.FindFilmsByIds;
import com.tp.sp.swapi.swapi.jsonschema.Films;
import com.tp.sp.swapi.swapiclient.clients.FilmsClient;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindFilmsByIdsSwapi implements FindFilmsByIds {

  private final FilmsClient filmsClient;
  private final FilmMapper filmMapper;

  @Override
  public Flux<Film> findAllByIds(Collection<Integer> ids) {
    return Flux.from(filmsClient.findAllByIds(ids))
        .map(Films::getResults)
        .flatMap(r -> Flux.fromStream(r.stream()))
        .map(filmMapper::toFilm);
  }
}
