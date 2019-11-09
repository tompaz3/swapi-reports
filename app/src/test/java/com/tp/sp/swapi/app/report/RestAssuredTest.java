package com.tp.sp.swapi.app.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tp.sp.swapi.app.AppTestTags;
import io.restassured.RestAssured;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

@Tag((AppTestTags.APP_INTEGRATION_TEST))
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class RestAssuredTest {

  @LocalServerPort
  private int port;

  protected ObjectMapper objectMapper = new ObjectMapper();

  @PostConstruct
  void init() {
    RestAssured.port = port;
  }
}
