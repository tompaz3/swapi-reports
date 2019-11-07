package com.tp.sp.swapi.swapiclient;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.vavr.Lazy;

public class JsonMapperProvider {

  private final Lazy<JsonMapper> provider;

  public JsonMapperProvider() {
    this.provider = Lazy.of(this::createJsonMapper);
  }

  public JsonMapper provide() {
    return provider.get();
  }

  private JsonMapper createJsonMapper() {
    return JsonMapper.builder()
        .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
        .build();
  }
}
