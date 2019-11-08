package com.tp.sp.swapi.domain.model;

import java.util.Set;
import lombok.Value;

@Value(staticConstructor = "of")
public class Person {

  private final Integer id;
  private final String name;
  private final Integer homeWorldId;
  private final Set<Integer> filmIds;
}
