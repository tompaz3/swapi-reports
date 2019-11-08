package com.tp.sp.swapi.domain;

public class QueryCriteriaEmptyException extends RuntimeException {

  private static final long serialVersionUID = -6296957944452894339L;
  private static final String MESSAGE = "QueryCriteria cannot be empty.";

  QueryCriteriaEmptyException() {
    super(MESSAGE);
  }
}
