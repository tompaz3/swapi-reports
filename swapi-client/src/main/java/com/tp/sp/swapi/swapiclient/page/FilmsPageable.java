package com.tp.sp.swapi.swapiclient.page;

import com.tp.sp.swapi.swapi.jsonschema.Films;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class FilmsPageable implements Pageable<Films> {

  private final Films value;

  @Override
  public Films value() {
    return value;
  }

  @Override
  public Pageable<Films> append(Pageable<Films> other) {
    val results = new ArrayList<>(value.getResults());
    results.addAll(other.value().getResults());
    return new FilmsPageable(other.value().withResults(results));
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
    return new FilmsPageable(value);
  }
}
