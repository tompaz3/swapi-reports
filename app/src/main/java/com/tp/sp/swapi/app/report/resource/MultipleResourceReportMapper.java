package com.tp.sp.swapi.app.report.resource;

import com.tp.sp.swapi.api.reports.Reports;
import com.tp.sp.swapi.domain.model.Report;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MultipleResourceReportMapper {

  private final ResourceReportMapper resourceReportMapper;

  Reports toReports(List<Report> reports) {
    return new Reports()
        .withEntries(
            reports.stream()
                .map(resourceReportMapper::toResourceReport)
                .collect(Collectors.toList())
        );
  }

}
