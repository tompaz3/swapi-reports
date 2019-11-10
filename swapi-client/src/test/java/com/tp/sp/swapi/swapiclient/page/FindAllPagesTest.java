package com.tp.sp.swapi.swapiclient.page;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.tp.sp.swapi.swapi.jsonschema.Film;
import com.tp.sp.swapi.swapi.jsonschema.Films;
import com.tp.sp.swapi.swapi.jsonschema.Planets;
import com.tp.sp.swapi.swapiclient.SwapiClientProperties;
import com.tp.sp.swapi.swapiclient.SwapiClientTestTags;
import com.tp.sp.swapi.swapiclient.SwapiGetMethodClient;
import com.tp.sp.swapi.swapiclient.SwapiUriBuilder;
import com.tp.sp.swapi.swapiclient.TestPropertiesHolder;
import com.tp.sp.swapi.swapiclient.TestSwapiGetMethodClientProvider;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

@Tag(SwapiClientTestTags.SWAPI_CLIENT_INTEGRATION_TEST)
class FindAllPagesTest {

  private SwapiGetMethodClient getMethodClient;
  private SwapiClientProperties swapiClientProperties;

  @BeforeEach
  void setUp() {
    swapiClientProperties = TestPropertiesHolder.instance().getSwapiClientProperties();
    getMethodClient = TestSwapiGetMethodClientProvider.instance().provide();
  }

  @DisplayName("given: find all for films with search phrase, "
      + "when: find all, "
      + "then: all with phrase found")
  @Test
  void givenFindAllForFilmsWithSearchPhraseWhenFindAllThenAllWithPhraseFound() {
    // given find all for films
    final FindAllPages<Films> findAllPages = new FindAllPages<>(getMethodClient);
    // and films uri
    val uri = SwapiUriBuilder.of(swapiClientProperties.getFilmsUri()).build();
    // and film search phrase
    val searchPhrase = "the";
    // and get first page mono
    final Mono<Pageable<Films>> firstPage = getMethodClient.get(uri, Films.class)
        .map(f -> FilmsPageable
            .of(f, film -> containsIgnoreCase(film.getTitle(), searchPhrase)));

    // when find all pages
    val filmsMono = findAllPages.findAllPages(firstPage);
    val films = filmsMono.block();

    // then results retrieved
    assertThat(films.getResults()).isNotEmpty();
    // and all results match the specified phrase
    assertThat(films.getResults().stream().map(Film::getTitle)
        .allMatch(t -> containsIgnoreCase(t, searchPhrase))).isTrue();
  }

  @DisplayName("given: find all for planets, "
      + "when: find all, "
      + "then: found more than one page found")
  @Test
  void givenFindAllPeopleWhenFindAllThenAllFound() {
    // given page size
    val pageSize = 10;
    // and find all for planets
    final FindAllPages<Planets> findAllPages = new FindAllPages<>(getMethodClient);
    // and films uri
    val uri = SwapiUriBuilder.of(swapiClientProperties.getPlanetsUri())
        .queryParam("search", "i")
        .build();
    // and find first page mono
    final Mono<Pageable<Planets>> firstPage = getMethodClient.get(uri, Planets.class)
        .map(PlanetsPageable::new);

    // when find all
    // when find all pages
    val mono = findAllPages.findAllPages(firstPage);
    val result = mono.block();

    // then some result found
    assertThat(result.getResults()).isNotEmpty();
    // and found more than single page
    assertThat(result.getResults()).hasSizeGreaterThan(pageSize);
  }
}