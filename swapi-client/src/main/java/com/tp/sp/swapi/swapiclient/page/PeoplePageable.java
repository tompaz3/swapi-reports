package com.tp.sp.swapi.swapiclient.page;

import com.tp.sp.swapi.swapi.jsonschema.People;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import lombok.val;

@RequiredArgsConstructor
public class PeoplePageable implements Pageable<People> {

  private final People value;

  @Override
  public People value() {
    return value;
  }

  @Override
  public Pageable<People> append(Pageable<People> other) {
    val results = new ArrayList<>(value.getResults());
    results.addAll(other.value().getResults());
    return new PeoplePageable(other.value().withResults(results));
  }

  @Override
  public String getNext() {
    return value.getNext();
  }

  @Override
  public Class<People> type() {
    return People.class;
  }

  @Override
  public Pageable<People> newPageable(People value) {
    return new PeoplePageable(value);
  }
}
