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
public class PersonEntity {

  @Column(name = "character_id")
  private Integer id;

  @Column(name = "character_name")
  private String name;
}
