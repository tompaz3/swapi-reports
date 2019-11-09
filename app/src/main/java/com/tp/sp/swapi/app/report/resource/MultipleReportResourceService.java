package com.tp.sp.swapi.app.report.resource;

import com.tp.sp.swapi.api.reports.QueryCriteria;
import com.tp.sp.swapi.api.reports.Reports;
import com.tp.sp.swapi.domain.actions.DeleteReports;
import com.tp.sp.swapi.domain.actions.GetManyReports;
import com.tp.sp.swapi.domain.actions.PutManyReports;
import com.tp.sp.swapi.domain.model.Report;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import reactor.core.publisher.Mono;

@Service
class MultipleReportResourceService {

  private final DeleteReports deleteReports;
  private final GetManyReports getReports;
  private final PutManyReports putManyReports;
  private final MultipleResourceReportMapper multipleResourceReportMapper;
  private final ResourceReportMapper resourceReportMapper;

  MultipleReportResourceService(@Qualifier("deleteManyReports") DeleteReports deleteReports,
      GetManyReports getReports,
      PutManyReports putManyReports,
      MultipleResourceReportMapper multipleResourceReportMapper,
      ResourceReportMapper resourceReportMapper) {
    this.deleteReports = deleteReports;
    this.getReports = getReports;
    this.putManyReports = putManyReports;
    this.multipleResourceReportMapper = multipleResourceReportMapper;
    this.resourceReportMapper = resourceReportMapper;
  }

  Mono<List<Reports>> getAll() {
    return getReports.getAll()
        .collectList()
        .map(reportsList -> reportsList.stream()
            .collect(Collectors.groupingBy(Report::getId, Collectors.toList()))
        )
        .map(groupedReports -> groupedReports.values().stream()
            .map(multipleResourceReportMapper::toReports)
            .collect(Collectors.toList())
        );
  }


  Mono<Reports> getById(int reportId) {
    return getReports.getById(reportId)
        .collectList()
        .map(multipleResourceReportMapper::toReports)
        .filter(r -> !CollectionUtils.isEmpty(r.getEntries()));
  }

  Mono<Reports> putReport(int reportId, QueryCriteria queryCriteria) {
    return putManyReports
        .putReports(reportId, resourceReportMapper.fromQueryCriteriaResource(queryCriteria))
        .collectList()
        .map(multipleResourceReportMapper::toReports)
        .filter(r -> !CollectionUtils.isEmpty(r.getEntries()));
  }

  Mono<Void> deleteAll() {
    return deleteReports.deleteAll();
  }

  Mono<Void> deleteById(int reportId) {
    return deleteReports.deleteById(reportId);
  }
}
