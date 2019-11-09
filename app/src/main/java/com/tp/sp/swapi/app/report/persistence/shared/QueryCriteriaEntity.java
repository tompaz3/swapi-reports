package com.tp.sp.swapi.app.report.persistence.shared;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class QueryCriteriaEntity {

  @Column(name = "query_criteria_character_phrase")
  private String characterPhrase;

  @Column(name = "query_criteria_planet_name")
  private String planetName;
}
