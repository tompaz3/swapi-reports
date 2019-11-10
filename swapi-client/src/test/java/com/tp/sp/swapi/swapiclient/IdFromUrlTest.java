package com.tp.sp.swapi.swapiclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.stream.Stream;
import lombok.val;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class IdFromUrlTest {


  @MethodSource("urlToIdSource")
  @ParameterizedTest
  void givenUrlWhenToIdThenMapped(String url, int id) {
    // given url
    // when map
    val mappedId = IdFromUrl.toId(url);

    // then mapped as expected
    assertThat(mappedId).isEqualTo(id);
  }

  static Stream<Arguments> urlToIdSource() {
    return Stream.of(
        arguments("https://swapi.co/api/people/1", 1),
        arguments("https://swapi.co/api/people/233", 233),
        arguments("https://swapi.co/api/people/178/", 178)
    );
  }
}