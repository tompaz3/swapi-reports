package com.tp.sp.swapi.swapiclient;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@RequiredArgsConstructor
public class SwapiGetMethodClient {

  private final HttpClient httpClient;
  private final SwapiResponseMapper responseMapper;

  /**
   * Executes HTTP GET method in a reactive manner, mapping response to given {@code responseType}.
   *
   * @param uri GET request URI.
   * @param responseType expected response object type.
   * @param <T> response object type.
   * @return response object mapped to the specified {@code responseType}.
   */
  public <T> Mono<T> get(String uri, Class<T> responseType) {
    return httpClient
        .headers((h) -> h.add(HttpHeaderNames.ACCEPT, HttpHeaderValues.APPLICATION_JSON))
        .get()
        .uri(uri)
        .responseContent()
        .aggregate()
        .asString()
        .map(content -> responseMapper.toSwapiResponse(content, responseType));
  }
}
