package com.tp.sp.swapi.app.report.persistence.v2;

import com.tp.sp.swapi.app.report.persistence.FilmEntity;
import com.tp.sp.swapi.app.report.persistence.PersonEntity;
import com.tp.sp.swapi.app.report.persistence.PlanetEntity;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Table(name = "report_entries_v2")
@Entity
public class ReportEntryEntityV2 {

  @Id
  @SequenceGenerator(name = "report_entries_v2_seq", sequenceName = "report_entries_v2_seq",
      allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "report_entries_v2_seq")
  private Integer id;

  @Embedded
  private PersonEntity person;

  @Embedded
  private PlanetEntity planet;

  @Embedded
  private FilmEntity film;

  @ManyToOne
  @JoinColumn(name = "report_id")
  private ReportEntityV2 report;
}
