package com.tp.sp.swapi.swapiclient;

import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpHeaderValues;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class SwapiGenericFindByNameClient {

  private final HttpClient httpClient;
  private final SwapiResponseMapper responseMapper;

  public SwapiGenericFindByNameClient(String baseUrl, SwapiResponseMapper responseMapper) {
    this.httpClient = HttpClient.create().baseUrl(baseUrl);
    this.responseMapper = responseMapper;
  }

  public <T> Mono<T> findByName(String uri, String name, Class<T> responseType) {
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
