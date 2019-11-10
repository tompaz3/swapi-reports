package com.tp.sp.swapi.swapiclient.clients.cached;

import static org.apache.commons.lang3.StringUtils.containsIgnoreCase;
import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.swapi.jsonschema.Person;
import com.tp.sp.swapi.swapiclient.SwapiClientProperties;
import com.tp.sp.swapi.swapiclient.SwapiClientTestTags;
import com.tp.sp.swapi.swapiclient.TestPropertiesHolder;
import com.tp.sp.swapi.swapiclient.TestSwapiGetMethodClientProvider;
import com.tp.sp.swapi.swapiclient.clients.http.PeopleHttpClient;
import com.tp.sp.swapi.swapiclient.page.FindAllPages;
import lombok.val;
import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

@Tag(SwapiClientTestTags.SWAPI_CLIENT_INTEGRATION_TEST)
class PeopleCacheClientTest {

  private SwapiClientProperties swapiClientProperties;
  private SwapiEtagClient swapiEtagClient;
  private PeopleHttpClient peopleHttpClient;

  @BeforeEach
  void setUp() {
    swapiClientProperties = TestPropertiesHolder.instance().getSwapiClientProperties();
    swapiEtagClient = new SwapiEtagClient(
        HttpClient.create().baseUrl(swapiClientProperties.getBaseUrl()));
    val getMethodClient = TestSwapiGetMethodClientProvider.instance().provide();
    peopleHttpClient = new PeopleHttpClient(new FindAllPages<>(getMethodClient), getMethodClient,
        swapiClientProperties.getPeopleUri());
  }

  @DisplayName("given: two different search phrases, "
      + "when: search twice, once for each, "
      + "then: both sets found")
  @Test
  void givenIdSetsWhenSearchForSetsThenFound() {
    // given freshly instantiated client
    val peopleCacheClient = new PeopleCacheClient(swapiClientProperties.getPeopleUri(),
        peopleHttpClient,
        swapiEtagClient);
    // and search phrases
    val firstName = "skywalker";
    val secondName = "darth";

    // when find
    val firstResults = peopleCacheClient.findByName(firstName).block();
    val secondResults = peopleCacheClient.findByName(secondName).block();

    // then both found
    assertThat(firstResults.getResults()).isNotEmpty();
    assertThat(secondResults.getResults()).isNotEmpty();
    // and both have valid names
    assertThat(firstResults.getResults().stream().map(Person::getName)
        .allMatch(n -> containsIgnoreCase(n, firstName)))
        .isTrue();
    assertThat(secondResults.getResults().stream().map(Person::getName)
        .allMatch(n -> containsIgnoreCase(n, secondName)))
        .isTrue();
  }

  @DisplayName("given: planets' name to search with no search executed before, "
      + "when: search is made twice, "
      + "then: first search lasts longer than the second one ")
  @Test
  void givenIdsAndNoSearchExecutedWhenSearchTwiceThenFirstLastsLonger() {
    // given freshly instantiated client
    val peopleCacheClient = new PeopleCacheClient(swapiClientProperties.getPeopleUri(),
        peopleHttpClient,
        swapiEtagClient);
    // and searched phrase
    val name = "skywalker";

    val firstStart = System.nanoTime();
    // when get first
    val firstResults = peopleCacheClient.findByName(name).block();
    val firstTime = System.nanoTime() - firstStart;

    // then next call lasts way shorter
    val secondStart = System.nanoTime();
    val secondResults = peopleCacheClient.findByName(name).block();
    val secondTime = System.nanoTime() - secondStart;
    System.out.printf("FirstTime = %d ns, SecondTime = %d ns", firstTime, secondTime);
    assertThat(secondTime).isLessThan(firstTime);

    // and both have same number of results
    assertThat(firstResults.getResults().size()).isEqualTo(secondResults.getResults().size());
    // and both contain same results
    AssertionsForInterfaceTypes.assertThat(firstResults.getResults())
        .containsAll(secondResults.getResults());
  }
}