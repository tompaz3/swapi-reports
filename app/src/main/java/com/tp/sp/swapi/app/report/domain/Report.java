package com.tp.sp.swapi.app.report.domain;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Report {

  private final int id;
  private final QueryCriteria queryCriteria;
  private final Film film;
  private final Person person;
  private final Planet planet;
}
