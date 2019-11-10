package com.tp.sp.swapi.swapiclient.clients.cached;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.tp.sp.swapi.swapiclient.SwapiClientProperties;
import com.tp.sp.swapi.swapiclient.SwapiClientTestTags;
import com.tp.sp.swapi.swapiclient.TestPropertiesHolder;
import com.tp.sp.swapi.swapiclient.TestSwapiGetMethodClientProvider;
import com.tp.sp.swapi.swapiclient.clients.http.FilmsHttpClient;
import com.tp.sp.swapi.swapiclient.page.FindAllPages;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

@Tag(SwapiClientTestTags.SWAPI_CLIENT_INTEGRATION_TEST)
class FilmsCacheClientTest {

  private SwapiClientProperties swapiClientProperties;
  private SwapiEtagClient swapiEtagClient;
  private FilmsHttpClient filmsClient;

  @BeforeEach
  void setUp() {
    swapiClientProperties = TestPropertiesHolder.instance().getSwapiClientProperties();
    swapiEtagClient = new SwapiEtagClient(
        HttpClient.create().baseUrl(swapiClientProperties.getBaseUrl()));
    val getMethodClient = TestSwapiGetMethodClientProvider.instance().provide();
    filmsClient = new FilmsHttpClient(new FindAllPages<>(getMethodClient), getMethodClient,
        swapiClientProperties.getFilmsUri());
  }

  @DisplayName("given: two different id sets, "
      + "when: search twice, once for each, "
      + "then: both sets found")
  @Test
  void givenIdSetsWhenSearchForSetsThenFound() {
    // given freshly instantiated client
    val filmsCacheClient = new FilmsCacheClient(swapiClientProperties.getFilmsUri(), filmsClient,
        swapiEtagClient);
    // and id sets
    val firstIds = List.of(1, 2, 3);
    val secondIds = List.of(2, 3, 4);

    val firstFilms = filmsCacheClient.findAllByIds(firstIds).block();
    val secondFilms = filmsCacheClient.findAllByIds(secondIds).block();

    // then all found
    assertThat(firstFilms.getResults()).hasSize(3);
    assertThat(secondFilms.getResults()).hasSize(3);
  }

  @DisplayName("given: film ids to search with no search executed before, "
      + "when: search is made twice, "
      + "then: first search lasts longer than the second one ")
  @Test
  void givenIdsAndNoSearchExecutedWhenSearchTwiceThenFirstLastsLonger() {
    // given freshly instantiated client
    val filmsCacheClient = new FilmsCacheClient(swapiClientProperties.getFilmsUri(), filmsClient,
        swapiEtagClient);
    // and some ids
    val ids = List.of(1, 2, 3, 4, 5, 6);

    val firstStart = System.nanoTime();
    // when get first
    val firstFilms = filmsCacheClient.findAllByIds(ids).block();
    val firstTime = System.nanoTime() - firstStart;

    // then next call lasts way shorter
    val secondStart = System.nanoTime();
    val secondFilms = filmsCacheClient.findAllByIds(ids).block();
    val secondTime = System.nanoTime() - secondStart;
    System.out.printf("FirstTime = %d ns, SecondTime = %d ns", firstTime, secondTime);
    assertThat(secondTime).isLessThan(firstTime);

    // and both have same number of films
    assertThat(firstFilms.getResults().size()).isEqualTo(secondFilms.getResults().size());
    // and both contain same results
    assertThat(firstFilms.getResults()).containsAll(secondFilms.getResults());
  }
}