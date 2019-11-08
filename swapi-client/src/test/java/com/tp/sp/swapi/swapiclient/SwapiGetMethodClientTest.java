package com.tp.sp.swapi.swapiclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.tp.sp.swapi.swapi.jsonschema.Film;
import com.tp.sp.swapi.swapi.jsonschema.Films;
import com.tp.sp.swapi.swapi.jsonschema.People;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(SwapiClientTestTags.SWAPI_CLIENT_INTEGRATION_TEST)
class SwapiGetMethodClientTest {

  private SwapiGetMethodClient client;
  private SwapiClientProperties swapiClientProperties;

  @BeforeEach
  void setUp() {
    swapiClientProperties = TestPropertiesHolder.instance().getSwapiClientProperties();
    val swapiResponseMapper = new SwapiResponseMapper(new JsonMapperProvider());
    client = new SwapiGetMethodClient(swapiClientProperties.getBaseUrl(),
        swapiResponseMapper);
  }

  @DisplayName("given: given valid name query param and uri, "
      + "when: get, "
      + "then: all records containing given name query value found")
  @Test
  void givenNameParameterAndUriWhenGetThenAllRecordsContainingNamePhraseFound() {
    // given query param value
    val name = "a";
    // and uri
    val uri = SwapiUriBuilder.of(swapiClientProperties.getFilmsUri())
        .queryParam("search", name)
        .build();

    // when get
    val response = client.get(uri, Films.class);
    val films = response.block();

    // then response mapped and returned
    assertThat(films).isNotNull();
    // and results are not empty
    assertThat(films.getResults()).isNotEmpty();

    // and all results contain name equal to given param
    val pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
    assertThat(films.getResults().stream().map(Film::getTitle).map(pattern::matcher)
        .allMatch(Matcher::find)).isTrue()
        .as("Results contain only films with name containing {}", name);
  }

  @DisplayName("given: invalid uri, "
      + "when: get, "
      + "then: exception thrown")
  @Test
  void givenInvalidUriWhenGetThenExceptionIsThrown() {
    // given invalid uri
    val uri = "http://localhost:12345/doesnotexist/";

    // when get
    val response = client.get(uri, People.class);
    // then exception is thrown
    assertThatThrownBy(response::block);
  }

  @DisplayName("given: non-existing name as search param, "
      + "when: get, "
      + "then: no records found")
  @Test
  void givenNonExistingNameWhenGetThenNothingFound() {
    // given query param value
    val name = "ajahwdwahdhwad==10329=2";
    // and uri
    val uri = SwapiUriBuilder.of(swapiClientProperties.getFilmsUri())
        .queryParam("search", name)
        .build();

    // when get
    val response = client.get(uri, Films.class);
    val films = response.block();

    // then result retrieved
    assertThat(films).isNotNull();
    // and no records found
    assertThat(films.getResults()).isEmpty();
  }
}