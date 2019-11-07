package com.tp.sp.swapi.swapiclient;

import io.vavr.Lazy;
import lombok.val;

final class TestSwapiGetMethodClientProvider {

  private static final TestSwapiGetMethodClientProvider INSTANCE
      = new TestSwapiGetMethodClientProvider();

  private final Lazy<SwapiGetMethodClient> provider;

  private TestSwapiGetMethodClientProvider() {
    this.provider = Lazy.of(this::create);
  }

  SwapiGetMethodClient provide() {
    return provider.get();
  }

  static TestSwapiGetMethodClientProvider instance() {
    return INSTANCE;
  }

  private SwapiGetMethodClient create() {
    val swapiClientProperties = TestPropertiesHolder.instance().getSwapiClientProperties();
    val swapiResponseMapper = new SwapiResponseMapper(
        new JsonMapperProvider().provide());
    return new SwapiGetMethodClient(swapiClientProperties.getBaseUrl(),
        swapiResponseMapper);
  }
}
