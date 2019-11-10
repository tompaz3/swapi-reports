package com.tp.sp.swapi.swapiclient.clients.http;

import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.swapi.jsonschema.Planet;
import com.tp.sp.swapi.swapiclient.SwapiClientTestTags;
import com.tp.sp.swapi.swapiclient.TestPropertiesHolder;
import com.tp.sp.swapi.swapiclient.TestSwapiGetMethodClientProvider;
import com.tp.sp.swapi.swapiclient.page.FindAllPages;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(SwapiClientTestTags.SWAPI_CLIENT_INTEGRATION_TEST)
class PlanetsHttpClientTest {

  private PlanetsHttpClient client;

  @BeforeEach
  void setUp() {
    val planetsUri = TestPropertiesHolder.instance().getSwapiClientProperties().getPlanetsUri();
    val getMethodClient = TestSwapiGetMethodClientProvider.instance().provide();
    client = new PlanetsHttpClient(new FindAllPages<>(getMethodClient), getMethodClient,
        planetsUri);
  }

  @DisplayName("given: valid planets name as query param, "
      + "when: find, "
      + "then: planets having such name found")
  @Test
  void givenValidNameWhenFindThenFound() {
    // given valid name
    val name = "Tatooine";
    // when find by name
    val response = client.findByName(name);
    val planets = response.block();

    // then response mapped and returned
    assertThat(planets).isNotNull();
    // and results are not empty
    assertThat(planets.getResults()).isNotEmpty();

    // and all results contain name equal to given param
    val pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
    assertThat(planets.getResults().stream().map(Planet::getName).map(pattern::matcher)
        .allMatch(Matcher::find)).isTrue()
        .as("Results contain only planets with name containing {}", name);
  }

  @Test
  void givenInvalidNameWhenFindThenNotFound() {
    // given invalid name
    val name = "AW DIUWu q298UdQ(*DU89Q#22rQWr2@";
    // when find by name
    val response = client.findByName(name);
    val planets = response.block();

    // then response mapped and returned
    assertThat(planets).isNotNull();
    // and results are empty
    assertThat(planets.getResults()).isEmpty();
  }
}