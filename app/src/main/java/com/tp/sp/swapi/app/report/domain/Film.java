package com.tp.sp.swapi.app.report.domain;

import lombok.Value;

@Value(staticConstructor = "of")
public class Film {

  private final int id;
  private final String name;
}
