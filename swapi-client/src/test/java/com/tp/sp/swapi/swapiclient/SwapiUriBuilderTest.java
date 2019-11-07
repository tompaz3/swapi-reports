package com.tp.sp.swapi.swapiclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Map;
import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SwapiUriBuilderTest {


  @MethodSource("uriBuilderTestParams")
  @ParameterizedTest
  void givenUriWithPathParamsWhenBuildThenSuccess(String baseUri, Map<String, String> pathVariables,
      Map<String, String> queryParams, String expectedUri) {
    // given builder with given params
    val builder = SwapiUriBuilder.of(baseUri);
    pathVariables.forEach(builder::pathVariable);
    queryParams.forEach(builder::queryParam);

    // when build
    val mappedUri = builder.build();

    // then mapped uri equal to expected
    assertThat(mappedUri).startsWith(expectedUri);
  }

  static Stream<Arguments> uriBuilderTestParams() {
    return Stream.of(
        arguments("http://example.com/people/{id}/",
            Map.of("id", "2"),
            Map.of("name", "Mickey", "lastName", "Mouse"),
            "http://example.com/people/2/?lastName=Mouse&name=Mickey"),
        arguments("http://example.com/people/{id}/",
            Map.of("id", "2"),
            Map.of(),
            "http://example.com/people/2/"),
        arguments("http://example.com/people/{id}/",
            Map.of(),
            Map.of("name", "Mickey", "lastName", "Mouse"),
            "http://example.com/people/{id}/?lastName=Mouse&name=Mickey"),
        arguments("http://example.com/people/{id}/",
            Map.of("id", "2"),
            Map.of("name", "Mickey", "lastName", "Mouse"),
            "http://example.com/people/2/?lastName=Mouse&name=Mickey")
    );
  }
}