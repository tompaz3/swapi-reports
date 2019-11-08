package com.tp.sp.swapi.domain.actions;

import com.tp.sp.swapi.domain.ReportRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteReportById {

  private final ReportRepository reportRepository;

  public void deleteById(int id) {
    reportRepository.deleteById(id);
  }
}
