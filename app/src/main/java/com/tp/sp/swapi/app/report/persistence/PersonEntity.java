package com.tp.sp.swapi.app.report.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PersonEntity {

  @Column(name = "character_id")
  private Integer id;

  @Column(name = "character_name")
  private String name;
}
