package com.tp.sp.swapi.app.report.config;

import com.tp.sp.swapi.app.report.persistence.multiple.MultipleReportEntityMapper;
import com.tp.sp.swapi.app.report.persistence.multiple.MultipleReportsEntityRepository;
import com.tp.sp.swapi.app.report.persistence.multiple.MultipleReportsJpaRepository;
import com.tp.sp.swapi.app.report.persistence.single.ReportEntityMapper;
import com.tp.sp.swapi.app.report.persistence.single.ReportEntityRepository;
import com.tp.sp.swapi.app.report.persistence.single.ReportsJpaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class PersistenceConfig {

  @Bean
  public ReportsJpaRepository jpaReportRepository(TransactionTemplate transactionTemplate,
      ReportEntityRepository reportEntityRepository,
      ReportEntityMapper reportEntityMapper) {
    return new ReportsJpaRepository(transactionTemplate, reportEntityRepository,
        reportEntityMapper);
  }

  @Bean
  public MultipleReportsJpaRepository multipleReportsJpaRepository(
      TransactionTemplate transactionTemplate,
      MultipleReportsEntityRepository multipleReportsEntityRepository,
      MultipleReportEntityMapper multipleReportEntityMapper) {
    return new MultipleReportsJpaRepository(transactionTemplate, multipleReportsEntityRepository,
        multipleReportEntityMapper);
  }
}
