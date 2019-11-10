package com.tp.sp.swapi.swapiclient.clients.cached;

import io.netty.handler.codec.http.HttpHeaderNames;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientResponse;

@RequiredArgsConstructor
public class SwapiEtagClient {

  private final HttpClient httpClient;

  /**
   * Gets Etag for the given uri.
   *
   * @param uri uri.
   * @return Etag.
   */
  Mono<String> getEtag(String uri) {
    return httpClient.head()
        .uri(uri)
        .response()
        .map(HttpClientResponse::responseHeaders)
        .map(h -> h.get(HttpHeaderNames.ETAG));
  }

}
