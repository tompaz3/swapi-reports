package com.tp.sp.swapi.swapiclient;

import com.fasterxml.jackson.databind.json.JsonMapper;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SwapiResponseMapper {

  private final JsonMapper jsonMapper;

  public <T> T toSwapiResponse(String content, Class<T> responseType) {
    return toPojo(content, responseType);
  }

  private <T> T toPojo(String content, Class<T> pojoType) {
    return Try.of(() -> jsonMapper.readValue(content, pojoType))
        .getOrElseThrow(
            (e) -> SwapiResponseMappingException.builder()
                .content(content)
                .typeName(pojoType)
                .cause(e)
                .build());
  }

}
