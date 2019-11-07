package com.tp.sp.swapi.swapiclient;

import com.tp.sp.swapi.swapi.jsonschema.Film;
import com.tp.sp.swapi.swapi.jsonschema.Films;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.val;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(SwapiClientTestTags.SWAPI_CLIENT_INTEGRATION_TEST)
class SwapiGenericFindByNameClientTest {

  private SwapiGenericFindByNameClient client;
  private SwapiClientProperties swapiClientProperties;

  @BeforeEach
  void setUp() {
    swapiClientProperties = TestPropertiesHolder.instance().getSwapiClientProperties();
    val swapiResponseMapper = new SwapiResponseMapper(
        new JsonMapperProvider().provide());
    client = new SwapiGenericFindByNameClient(swapiClientProperties.getBaseUrl(),
        swapiResponseMapper);
  }

  @Test
  void test() {
    val name = "a";
    val uri = SwapiUriBuilder.of(swapiClientProperties.getFilmsUri())
        .queryParam("search", name)
        .build();
    val response = client.findByName(uri, name, Films.class);

    val films = response.block();

    Assertions.assertThat(films).isNotNull();
    Assertions.assertThat(films.getResults()).isNotEmpty();

    val pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
    Assertions.assertThat(films.getResults().stream().map(Film::getTitle).map(pattern::matcher)
        .filter(Matcher::find).count()).isEqualTo(films.getResults().size())
        .as("Results contain only films with name containing {}", name);
  }
}