package com.tp.sp.swapi.swapiclient;

import reactor.netty.http.client.HttpClient;

public interface HttpClientFactory {

  HttpClient create();
}
