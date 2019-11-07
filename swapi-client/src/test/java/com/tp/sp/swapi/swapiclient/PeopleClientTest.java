package com.tp.sp.swapi.swapiclient;

import static org.assertj.core.api.Assertions.assertThat;

import com.tp.sp.swapi.swapi.jsonschema.Person;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag(SwapiClientTestTags.SWAPI_CLIENT_INTEGRATION_TEST)
class PeopleClientTest {

  private PeopleClient client;

  @BeforeEach
  void setUp() {
    val peopleUri = TestPropertiesHolder.instance().getSwapiClientProperties().getPeopleUri();
    val genericFindByNameClient = TestSwapiGetMethodClientProvider.instance().provide();
    client = new PeopleClient(genericFindByNameClient, peopleUri);
  }

  @DisplayName("given: valid people name as query param, "
      + "when: find, "
      + "then: people having such name found")
  @Test
  void givenValidNameWhenFindThenFound() {
    // given valid name
    val name = "skywalker";
    // when find by name
    val response = client.findByName(name);
    val people = response.block();

    // then response mapped and returned
    assertThat(people).isNotNull();
    // and results are not empty
    assertThat(people.getResults()).isNotEmpty();

    // and all results contain name equal to given param
    val pattern = Pattern.compile(name, Pattern.CASE_INSENSITIVE);
    assertThat(people.getResults().stream().map(Person::getName).map(pattern::matcher)
        .filter(Matcher::find).count()).isEqualTo(people.getResults().size())
        .as("Results contain only people with name containing {}", name);
  }

  @Test
  void givenInvalidNameWhenFindThenNotFound() {
    // given invalid name
    val name = "wie8w93ue8mw9uWRu#22rQWr2@";
    // when find by name
    val response = client.findByName(name);
    val people = response.block();

    // then response mapped and returned
    assertThat(people).isNotNull();
    // and results are empty
    assertThat(people.getResults()).isEmpty();
  }
}