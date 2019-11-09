package com.tp.sp.swapi.app.report.persistence.multiple;

import com.tp.sp.swapi.app.report.persistence.shared.FilmEntity;
import com.tp.sp.swapi.app.report.persistence.shared.PersonEntity;
import com.tp.sp.swapi.app.report.persistence.shared.PlanetEntity;
import com.tp.sp.swapi.app.report.persistence.shared.QueryCriteriaEntity;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
@Table(name = "multiple_reports")
@Entity
class MultipleReportsEntity {

  @Id
  @SequenceGenerator(name = "multipleReportsSeq",
      sequenceName = "multiple_reports_seq",
      initialValue = 1, allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "multipleReportsSeq")
  private Integer id;

  @NotNull
  @Column(name = "report_id")
  private Integer reportId;

  @Embedded
  private QueryCriteriaEntity queryCriteria;

  @Embedded
  private PersonEntity person;

  @Embedded
  private PlanetEntity planet;

  @Embedded
  private FilmEntity film;
}
