package com.tp.sp.swapi.app.report.config;

import com.tp.sp.swapi.app.report.persistence.multiple.MultipleReportsJpaRepository;
import com.tp.sp.swapi.domain.actions.DeleteReports;
import com.tp.sp.swapi.domain.actions.GetManyReports;
import com.tp.sp.swapi.domain.actions.PutManyReports;
import com.tp.sp.swapi.domain.generate.GenerateReport;
import com.tp.sp.swapi.domain.model.Report;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import reactor.core.publisher.Flux;

@DependsOn(value = {
    "multipleReportsGenerator",
    "multipleReportsEntityRepository"
})
@Configuration
public class ActionsManyConfig {

  @Bean
  public GetManyReports getManyReports(
      MultipleReportsJpaRepository multipleReportsEntityRepository) {
    return new GetManyReports(multipleReportsEntityRepository);
  }

  @Bean
  public DeleteReports deleteManyReports(
      MultipleReportsJpaRepository multipleReportsEntityRepository) {
    return new DeleteReports(multipleReportsEntityRepository);
  }

  @Bean
  public PutManyReports putManyReports(
      @Qualifier("multipleReportsGenerator") GenerateReport<Flux<Report>> multipleReportsGenerator,
      MultipleReportsJpaRepository multipleReportsEntityRepository) {
    return new PutManyReports(multipleReportsGenerator, multipleReportsEntityRepository,
        multipleReportsEntityRepository);
  }
}
