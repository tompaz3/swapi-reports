package com.tp.sp.swapi.domain.port;

import com.tp.sp.swapi.domain.model.Film;
import java.util.Collection;
import reactor.core.publisher.Flux;

public interface FindFilmsByIds {

  Flux<Film> findAllByIds(Collection<Integer> ids);
}
