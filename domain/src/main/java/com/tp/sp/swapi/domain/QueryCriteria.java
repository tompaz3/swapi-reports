package com.tp.sp.swapi.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class QueryCriteria {

  private String characterPhrase;
  private String planetName;
}
