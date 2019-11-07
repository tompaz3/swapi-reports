package com.tp.sp.swapi.app.report.persistence;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class FilmEntity {

  @Column(name = "film_id")
  private Integer id;

  @Column(name = "film_name")
  private String name;
}
