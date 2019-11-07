package com.tp.sp.swapi.swapiclient;

import io.vavr.Lazy;
import lombok.val;

public final class TestSwapiGenericFindByNameClientProvider {

  private static final TestSwapiGenericFindByNameClientProvider INSTANCE
      = new TestSwapiGenericFindByNameClientProvider();

  private final Lazy<SwapiGetMethodClient> provider;

  private TestSwapiGenericFindByNameClientProvider() {
    this.provider = Lazy.of(this::create);
  }

  public SwapiGetMethodClient provide() {
    return provider.get();
  }

  public static TestSwapiGenericFindByNameClientProvider instance() {
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
