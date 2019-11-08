package com.tp.sp.swapi.app.report.find.mapping;

import static com.tp.sp.swapi.app.report.find.mapping.IdFromUrl.toId;

import com.tp.sp.swapi.domain.model.Film;

public class FilmMapper {

  public Film toFilm(com.tp.sp.swapi.swapi.jsonschema.Film film) {
    return Film.of(toId(film.getUrl()), film.getTitle());
  }
}
