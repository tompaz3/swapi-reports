package com.tp.sp.swapi.app.report.persistence;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.EqualsAndHashCode.Include;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "reports")
@Entity
public class ReportEntity {

  @NotNull
  @Include
  @Id
  private Integer id;

  @Embedded
  private QueryCriteriaEntity queryCriteria;

  @Embedded
  private PersonEntity person;

  @Embedded
  private PlanetEntity planet;

  @Embedded
  private FilmEntity film;
}
