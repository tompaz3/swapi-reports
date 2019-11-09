package com.tp.sp.swapi.app.report.config;

import com.tp.sp.swapi.app.report.persistence.single.ReportsJpaRepository;
import com.tp.sp.swapi.domain.actions.DeleteReports;
import com.tp.sp.swapi.domain.actions.GetReports;
import com.tp.sp.swapi.domain.actions.PutReport;
import com.tp.sp.swapi.domain.generate.GenerateReport;
import com.tp.sp.swapi.domain.model.Report;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import reactor.core.publisher.Mono;

@DependsOn(value = {
    "singleReportGenerator",
    "jpaReportRepository"
})
@Configuration
public class ActionsConfig {

  @Bean
  public GetReports getReports(ReportsJpaRepository reportsJpaRepository) {
    return new GetReports(reportsJpaRepository);
  }

  @Bean
  public DeleteReports deleteReports(ReportsJpaRepository reportsJpaRepository) {
    return new DeleteReports(reportsJpaRepository);
  }

  @Bean
  public PutReport putReport(ReportsJpaRepository reportsJpaRepository,
      @Qualifier("singleReportGenerator") GenerateReport<Mono<Report>> singleReportGenerator) {
    return new PutReport(singleReportGenerator, reportsJpaRepository, reportsJpaRepository);
  }
}
