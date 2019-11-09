package com.tp.sp.swapi.app.report.api.multiple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonNaming(SnakeCaseStrategy.class)
public class MultipleReport {

  @JsonProperty("character_id")
  private String personId;
  @JsonProperty("person_name")
  private String personName;
  private String planetId;
  private String planetName;
  private List<MultipleFilm> films;
}
