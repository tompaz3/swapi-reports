package com.tp.sp.swapi.swapiclient;

import static lombok.AccessLevel.PRIVATE;

import lombok.NoArgsConstructor;

public class SwapiResponseMappingException extends RuntimeException {

  private static final long serialVersionUID = 7352580037020199042L;
  private static final String MESSAGE_PATTERN = "Could not map Swapi response to expected type "
      + "[Content=%s][Type=%s].";

  private SwapiResponseMappingException(String message, Throwable cause) {
    super(message, cause);
  }

  public static SwapiResponseMappingExceptionBuilder builder() {
    return new SwapiResponseMappingExceptionBuilder();
  }

  @NoArgsConstructor(access = PRIVATE)
  public static class SwapiResponseMappingExceptionBuilder {

    private String content;
    private String typeName;
    private Throwable cause;

    public SwapiResponseMappingExceptionBuilder content(String content) {
      this.content = content;
      return this;
    }

    public SwapiResponseMappingExceptionBuilder typeName(Class<?> typeName) {
      this.typeName = typeName.getName();
      return this;
    }

    public SwapiResponseMappingExceptionBuilder cause(Throwable cause) {
      this.cause = cause;
      return this;
    }

    public SwapiResponseMappingException build() {
      return new SwapiResponseMappingException(String.format(MESSAGE_PATTERN, content, typeName),
          cause);
    }
  }
}
