package com.tp.sp.swapi.app.report.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class QueryCriteria {

  private String characterPhrase;
  private String planetName;
}
