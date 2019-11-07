package com.tp.sp.swapi.app.report.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class PlanetEntity {

  @Column(name = "planet_id")
  private Integer id;

  @Column(name = "planet_name")
  private String name;
}
