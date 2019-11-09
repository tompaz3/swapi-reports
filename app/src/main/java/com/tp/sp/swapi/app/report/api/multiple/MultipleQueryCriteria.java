package com.tp.sp.swapi.app.report.api.multiple;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@Data(staticConstructor = "of")
@JsonNaming(SnakeCaseStrategy.class)
public class MultipleQueryCriteria {

  @JsonProperty("query_criteria_character_phrase")
  private String characterPhrase;
  @JsonProperty("query_criteria_planet_name")
  private String planetName;
}
