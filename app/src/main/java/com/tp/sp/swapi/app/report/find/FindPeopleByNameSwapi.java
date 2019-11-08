package com.tp.sp.swapi.app.report.find;

import com.tp.sp.swapi.app.report.find.mapping.PersonMapper;
import com.tp.sp.swapi.domain.FindPeopleByName;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.swapi.jsonschema.People;
import com.tp.sp.swapi.swapiclient.PeopleClient;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class FindPeopleByNameSwapi implements FindPeopleByName {

  private final PeopleClient peopleClient;
  private final PersonMapper personMapper;

  @Override
  public Flux<Person> findByName(String name) {
    return Flux.from(peopleClient.findByName(name))
        .map(People::getResults)
        .flatMap(r -> Flux.fromStream(r.stream()))
        .map(personMapper::toPerson);
  }
}
