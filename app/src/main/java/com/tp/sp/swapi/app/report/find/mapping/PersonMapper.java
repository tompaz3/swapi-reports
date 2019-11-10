package com.tp.sp.swapi.app.report.find.mapping;

import static com.tp.sp.swapi.swapiclient.IdFromUrl.toId;

import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.swapiclient.IdFromUrl;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonMapper {

  public Person toPerson(com.tp.sp.swapi.swapi.jsonschema.Person person) {
    return Person.of(toId(person.getUrl()), person.getName(),
        toId(person.getHomeworld()), toFilmIds(person.getFilms()));
  }

  private Set<Integer> toFilmIds(Collection<String> filmsUrls) {
    return filmsUrls.stream()
        .map(IdFromUrl::toId)
        .collect(Collectors.toSet());
  }
}
