package com.tp.sp.swapi.domain.model;

import lombok.Value;
import org.apache.commons.lang3.StringUtils;

@Value(staticConstructor = "of")
public class QueryCriteria {

  private String characterPhrase;
  private String planetName;

  public boolean isEmpty() {
    return StringUtils.isAllEmpty(characterPhrase, planetName);
  }
}
