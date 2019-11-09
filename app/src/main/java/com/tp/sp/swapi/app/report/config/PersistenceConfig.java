package com.tp.sp.swapi.app.report.config;

import com.tp.sp.swapi.app.report.persistence.single.ReportEntityMapper;
import com.tp.sp.swapi.app.report.persistence.single.ReportEntityRepository;
import com.tp.sp.swapi.app.report.persistence.single.ReportsJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfig {

  @Bean
  public ReportsJpaRepository jpaReportRepository(ReportEntityRepository reportEntityRepository,
      ReportEntityMapper reportEntityMapper) {
    return new ReportsJpaRepository(reportEntityRepository, reportEntityMapper);
  }
}
