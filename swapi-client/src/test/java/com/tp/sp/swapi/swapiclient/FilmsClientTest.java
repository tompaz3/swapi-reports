package com.tp.sp.swapi.swapiclient;

import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.swapi.jsonschema.Film;
import com.tp.sp.swapi.swapiclient.page.FindAllPages;
import java.util.List;
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
    val getMethodClient = TestSwapiGetMethodClientProvider.instance().provide();
    client = new FilmsClient(new FindAllPages<>(getMethodClient), getMethodClient, filmsUri);
  }

  @DisplayName("given: films client, "
      + "when: find all, "
      + "then: films found")
  @Test
  void givenUriWhenFindAllThenFilmsFound() {
    // given expected film ids
    val ids = List.of(1, 2, 3);
    // when find
    val response = client.findAllByIds(ids);
    val films = response.block();

    // then response mapped and returned
    assertThat(films).isNotNull();
    // and results are not empty
    assertThat(films.getResults()).isNotEmpty();
    // and results contain only film with expecetd ids
    assertThat(films.getResults().stream().map(Film::getUrl)
        .map(IdFromUrl::toId).allMatch(ids::contains)).isTrue();
  }
}