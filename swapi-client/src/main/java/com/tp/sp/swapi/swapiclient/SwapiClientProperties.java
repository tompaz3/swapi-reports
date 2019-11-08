package com.tp.sp.swapi.swapiclient;

import lombok.Builder;
import lombok.Value;
import lombok.With;

@With
@Builder
@Value
public class SwapiClientProperties {

  private final String baseUrl;
  private final String peopleUri;
  private final String filmsUri;
  private final String planetsUri;
}
