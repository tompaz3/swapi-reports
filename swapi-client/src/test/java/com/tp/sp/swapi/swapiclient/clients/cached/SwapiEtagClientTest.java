package com.tp.sp.swapi.swapiclient.clients.cached;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.tp.sp.swapi.swapiclient.SwapiClientProperties;
import com.tp.sp.swapi.swapiclient.SwapiClientTestTags;
import com.tp.sp.swapi.swapiclient.SwapiUriBuilder;
import com.tp.sp.swapi.swapiclient.TestPropertiesHolder;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

@Tag(SwapiClientTestTags.SWAPI_CLIENT_INTEGRATION_TEST)
class SwapiEtagClientTest {

  private SwapiEtagClient client;
  private SwapiClientProperties swapiClientProperties;

  @BeforeEach
  void setUp() {
    swapiClientProperties = TestPropertiesHolder.instance().getSwapiClientProperties();
    client = new SwapiEtagClient(HttpClient.create().baseUrl(swapiClientProperties.getBaseUrl()));
  }

  @DisplayName("given: valid uri, "
      + "when: get etag, "
      + "then: etag returned")
  @Test
  void givenValidUriWhenGetEtagThenFound() {
    // given uri
    val uri = SwapiUriBuilder.of(swapiClientProperties.getFilmsUri()).build();

    // when get
    val response = client.getEtag(uri);
    val etag = response.block();

    // then etag found
    assertThat(etag).isNotEmpty();
  }
}