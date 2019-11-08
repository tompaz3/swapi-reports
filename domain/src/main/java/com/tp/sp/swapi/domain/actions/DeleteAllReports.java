package com.tp.sp.swapi.domain.actions;

import com.tp.sp.swapi.domain.ReportRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteAllReports {

  private final ReportRepository reportRepository;

  public void deleteAll() {
    reportRepository.deleteAll();
  }
}
