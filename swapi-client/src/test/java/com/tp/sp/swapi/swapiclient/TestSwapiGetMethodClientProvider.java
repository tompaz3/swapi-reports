package com.tp.sp.swapi.swapiclient;

import io.vavr.Lazy;
import lombok.val;

public final class TestSwapiGetMethodClientProvider {

  private static final TestSwapiGetMethodClientProvider INSTANCE
      = new TestSwapiGetMethodClientProvider();

  private final Lazy<SwapiGetMethodClient> provider;

  private TestSwapiGetMethodClientProvider() {
    this.provider = Lazy.of(this::create);
  }

  public SwapiGetMethodClient provide() {
    return provider.get();
  }

  private SwapiGetMethodClient create() {
    val swapiClientProperties = TestPropertiesHolder.instance().getSwapiClientProperties();
    val swapiResponseMapper = new SwapiResponseMapper(new JsonMapperProvider());
    return new SwapiGetMethodClient(swapiClientProperties.getBaseUrl(),
        swapiResponseMapper);
  }

  public static TestSwapiGetMethodClientProvider instance() {
    return INSTANCE;
  }
}
