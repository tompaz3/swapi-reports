package com.tp.sp.swapi.swapiclient;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonMapperProviderTest {

  private JsonMapperProvider provider = new JsonMapperProvider();

  @SneakyThrows
  @DisplayName("given: correct content for deserialization, "
      + "when: readValue, "
      + "then: read successfully and deserialized object is equal to expected")
  @Test
  void givenCorrectContentWhenReadValueThenSuccess() {
    // given content for deserialization
    val name = "someName";
    val value = "someValue";
    val forDeserialization = format("{\"name\": \"%s\", \"value\": \"%s\"}", name, value);
    // and expected deserialized object
    val expected = DeserializedObject.builder().name(name).value(value).build();

    // when deserialize
    val deserialized = provider.provide().readValue(forDeserialization, DeserializedObject.class);

    // then deserialized is equal to expected
    assertThat(deserialized).isEqualTo(expected);
  }

  @SneakyThrows
  @DisplayName("given: content with unexpected property, "
      + "when: readValue, "
      + "then: read successfully and deserialized object is equal to expected")
  @Test
  void givenContentWithExtraPropertyWhenReadValueThenSuccess() {
    // given content for deserialization
    val name = "someName";
    val value = "someValue";
    val forDeserialization = format(
        "{\"name\": \"%s\", \"value\": \"%s\", \"unexpectedProperty\": \"unexpectedValue\"}",
        name, value);
    // and expected deserialized object
    val expected = DeserializedObject.builder().name(name).value(value).build();

    // when deserialize
    val deserialized = provider.provide().readValue(forDeserialization, DeserializedObject.class);

    // then deserialized is equal to expected
    assertThat(deserialized).isEqualTo(expected);
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Data
  private static class DeserializedObject {

    private String name;
    private String value;
  }
}