package com.tp.sp.swapi.app.report.persistence.single;

import io.vavr.control.Option;
import org.springframework.dao.EmptyResultDataAccessException;

public class ReportDeletionException extends RuntimeException {

  private static final long serialVersionUID = -4117496663927734689L;

  private ReportDeletionException(Throwable cause) {
    super(cause);
  }

  /**
   * ReportDeletionException static constructor. Returns {@link Option} containing
   * ReportDeletionException if {@code cause} is different from {@link
   * EmptyResultDataAccessException}. Otherwise returns {@link Option#none()}.
   *
   * <p>Calling delete for non-existing entity is allowed.
   *
   * @param cause caught during entity deletion.
   * @return {@link Option} containing ReportDeletionException if {@code cause} is different from
   * {@link EmptyResultDataAccessException}. {@link Option#none()} otherwise.
   */
  public static Option<ReportDeletionException> of(Throwable cause) {
    return cause instanceof EmptyResultDataAccessException
        ? Option.none()
        : Option.of(new ReportDeletionException(cause));
  }
}
