package com.tp.sp.swapi.swapiclient.page;

import static lombok.AccessLevel.PRIVATE;

import com.tp.sp.swapi.swapi.jsonschema.Film;
import com.tp.sp.swapi.swapi.jsonschema.Films;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor(access = PRIVATE)
public class FilmsPageable implements Pageable<Films> {

  private final Films value;
  private final Predicate<Film> filter;

  @Override
  public Films value() {
    return value;
  }

  @Override
  public Pageable<Films> append(Pageable<Films> other) {
    val results = new ArrayList<>(value.getResults());
    results.addAll(filterFilms(other.value().getResults()));
    return new FilmsPageable(other.value().withResults(results), filter);
  }

  private List<Film> filterFilms(List<Film> films) {
    return films.stream().filter(filter).collect(Collectors.toList());
  }

  @Override
  public String getNext() {
    return value.getNext();
  }

  @Override
  public Class<Films> type() {
    return Films.class;
  }

  @Override
  public Pageable<Films> newPageable(Films value) {
    return new FilmsPageable(value, filter);
  }

  public static FilmsPageable of(Films value, Predicate<Film> filter) {
    return new FilmsPageable(filterFirstPageResults(value, filter), filter);
  }

  private static Films filterFirstPageResults(Films films, Predicate<Film> filter) {
    val firstPageFilms = films.getResults().stream()
        .filter(filter)
        .collect(Collectors.toList());
    return films.withResults(firstPageFilms);
  }
}
