package com.tp.sp.swapi.domain;

import com.tp.sp.swapi.domain.model.Person;
import reactor.core.publisher.Flux;

public interface FindPeopleByName {

  Flux<Person> findByName(String name);
}
