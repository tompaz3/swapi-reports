package com.tp.sp.swapi.app.report.api.multiple;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class MultipleFilm {

  private String filmId;
  private String filmName;
}
