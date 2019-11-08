package com.tp.sp.swapi.domain.model;

import lombok.Value;

@Value(staticConstructor = "of")
public class Film {

  private final Integer id;
  private final String name;
}
