package com.tp.sp.swapi.app.report.persistence.v3;

import com.tp.sp.swapi.app.report.persistence.QueryCriteriaEntity;
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
@Table(name = "reports_v3")
@Entity
public class ReportEntityV3 {

  @NotNull
  @Include
  @Id
  private Integer id;

  @Embedded
  private QueryCriteriaEntity queryCriteria;
}
