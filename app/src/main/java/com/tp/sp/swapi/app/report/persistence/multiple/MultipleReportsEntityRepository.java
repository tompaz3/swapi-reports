package com.tp.sp.swapi.app.report.persistence.multiple;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MultipleReportsEntityRepository extends
    JpaRepository<MultipleReportsEntity, Integer> {

  Optional<MultipleReportsEntity> findFirstByReportId(int reportId);

  List<MultipleReportsEntity> findAllByReportId(int reportId);

  void deleteAllByReportId(int reportId);
}
