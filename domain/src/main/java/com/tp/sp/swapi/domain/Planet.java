package com.tp.sp.swapi.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Planet {

  private final int id;
  private final String name;
}
