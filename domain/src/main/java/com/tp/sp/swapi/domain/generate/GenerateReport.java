package com.tp.sp.swapi.domain.generate;

import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import org.reactivestreams.Publisher;

public interface GenerateReport<K extends Publisher<Report>> {

  K generateReport(int reportId, QueryCriteria queryCriteria);
}
