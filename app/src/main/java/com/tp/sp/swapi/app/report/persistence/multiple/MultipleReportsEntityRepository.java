package com.tp.sp.swapi.app.report.persistence.multiple;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MultipleReportsEntityRepository extends
    JpaRepository<MultipleReportsEntity, Integer> {

}
