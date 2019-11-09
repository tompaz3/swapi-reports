package com.tp.sp.swapi.app.report.api.multiple;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.SnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.tp.sp.swapi.api.reports.QueryCriteria;
import lombok.Data;

@Data
@JsonNaming(SnakeCaseStrategy.class)
public class MultipleReports {

  private int reportId;
  private QueryCriteria queryCriteria;
}
