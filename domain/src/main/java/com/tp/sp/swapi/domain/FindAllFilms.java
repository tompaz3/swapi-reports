package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Film;
import reactor.core.publisher.Flux;

public interface FindAllFilms {

  Flux<Film> findAll();
}
