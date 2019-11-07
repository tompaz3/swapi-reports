package com.tp.sp.swapi.swapiclient;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(SwapiClientTestTags.SWAPI_CLIENT_INTEGRATION_TEST)
class FilmsClientTest {

  private FilmsClient client;

  @BeforeEach
  void setUp() {
    val filmsUri = TestPropertiesHolder.instance().getSwapiClientProperties().getFilmsUri();
    val genericFindByNameClient = TestSwapiGetMethodClientProvider.instance().provide();
    client = new FilmsClient(genericFindByNameClient, filmsUri);
  }

  @DisplayName("given: films client, "
      + "when: find all, "
      + "then: films found")
  @Test
  void givenUriWhenFindAllThenFilmsFound() {
    // given films client
    // when find
    val response = client.findAll();
    val films = response.block();

    // then response mapped and returned
    assertThat(films).isNotNull();
    // and results are not empty
    assertThat(films.getResults()).isNotEmpty();
  }
}