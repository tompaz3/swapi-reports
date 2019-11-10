package com.tp.sp.swapi.app.report.swapi;

import com.tp.sp.swapi.app.report.properties.SwapiProperties;
import com.tp.sp.swapi.swapiclient.HttpClientFactory;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.stereotype.Component;
import reactor.netty.Connection;
import reactor.netty.http.client.HttpClient;
import reactor.netty.http.client.HttpClientRequest;
import reactor.netty.http.client.HttpClientResponse;

@Component
public class SwapiHttpClientFactory implements HttpClientFactory {

  private final String baseSwapiUrl;
  private final LoggingHandler requestLoggingHandler =
      new LoggingHandler("SwapiRequestLogger", LogLevel.INFO);
  private final LoggingHandler responseLoggingHandler =
      new LoggingHandler("SwapiResponseLogger", LogLevel.INFO);

  public SwapiHttpClientFactory(SwapiProperties swapiProperties) {
    this.baseSwapiUrl = swapiProperties.getClient().getBaseUrl();
  }

  @Override
  public HttpClient create() {
    return HttpClient.create().baseUrl(baseSwapiUrl)
        .doOnResponse(this::logResponse)
        .doOnRequest(this::logRequest);
  }

  private void logRequest(HttpClientRequest httpClientRequest, Connection connection) {
    connection.addHandler(requestLoggingHandler);
  }

  private void logResponse(HttpClientResponse response, Connection connection) {
    connection.addHandler(responseLoggingHandler);
  }

}
