package com.tp.sp.swapi.swapiclient.page;

import com.tp.sp.swapi.swapi.jsonschema.Planets;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class PlanetsPageable implements Pageable<Planets> {

  private final Planets value;

  @Override
  public Planets value() {
    return value;
  }

  @Override
  public Pageable<Planets> append(Pageable<Planets> other) {
    val results = new ArrayList<>(value.getResults());
    results.addAll(other.value().getResults());
    return new PlanetsPageable(other.value().withResults(results));
  }

  @Override
  public String getNext() {
    return value.getNext();
  }

  @Override
  public Class<Planets> type() {
    return Planets.class;
  }

  @Override
  public Pageable<Planets> newPageable(Planets value) {
    return new PlanetsPageable(value);
  }
}
