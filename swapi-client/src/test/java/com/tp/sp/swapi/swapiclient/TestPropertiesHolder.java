package com.tp.sp.swapi.swapiclient;

import static java.util.Map.entry;
import static java.util.Map.ofEntries;

import com.tp.sp.swapi.swapiclient.SwapiClientProperties.SwapiClientPropertiesBuilder;
import io.vavr.Function2;
import java.io.InputStream;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.val;

public final class TestPropertiesHolder {

  private static final String TEST_PROPERTIES_FILE = "swapi-client-test.properties";
  @SuppressWarnings("checkstyle:LineLength")
  private static final Map<String, Function2<SwapiClientPropertiesBuilder, String, SwapiClientPropertiesBuilder>> PROPERTY_TO_SWAPI_CLIENT_PROPERTIES_MAPPING =
      ofEntries(
          entry("baseUri", SwapiClientPropertiesBuilder::baseUrl),
          entry("peopleUri", SwapiClientPropertiesBuilder::peopleUri),
          entry("filmsUri", SwapiClientPropertiesBuilder::filmsUri),
          entry("planetsUri", SwapiClientPropertiesBuilder::planetsUri)
      );

  private static final TestPropertiesHolder INSTANCE = new TestPropertiesHolder();

  @Getter
  private final SwapiClientProperties swapiClientProperties;

  private TestPropertiesHolder() {
    this.swapiClientProperties = loadProperties();
  }

  @SneakyThrows
  private SwapiClientProperties loadProperties() {
    val properties = new Properties();
    try (final InputStream is = this.getClass().getClassLoader()
        .getResourceAsStream(TEST_PROPERTIES_FILE)) {
      properties.load(is);
    }
    return mapProperties(properties);
  }

  private SwapiClientProperties mapProperties(Properties properties) {
    return PROPERTY_TO_SWAPI_CLIENT_PROPERTIES_MAPPING.entrySet()
        .stream().reduce(SwapiClientProperties.builder(),
            (builder, e) -> applyProperty(properties, builder, e),
            (a, b) -> a)
        .build();
  }

  @SuppressWarnings("checkstyle:LineLength")
  private SwapiClientPropertiesBuilder applyProperty(Properties properties,
      SwapiClientPropertiesBuilder builder,
      Entry<String, Function2<SwapiClientPropertiesBuilder, String, SwapiClientPropertiesBuilder>> e) {
    return e.getValue().apply(builder, properties.getProperty(e.getKey()));
  }

  public static TestPropertiesHolder instance() {
    return INSTANCE;
  }
}
