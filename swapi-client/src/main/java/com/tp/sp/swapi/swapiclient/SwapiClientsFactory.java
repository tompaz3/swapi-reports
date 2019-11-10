package com.tp.sp.swapi.swapiclient;

import com.tp.sp.swapi.swapiclient.clients.FilmsClient;
import com.tp.sp.swapi.swapiclient.clients.PeopleClient;
import com.tp.sp.swapi.swapiclient.clients.PlanetsClient;
import com.tp.sp.swapi.swapiclient.clients.cached.FilmsCacheClient;
import com.tp.sp.swapi.swapiclient.clients.cached.PeopleCacheClient;
import com.tp.sp.swapi.swapiclient.clients.cached.PlanetsCacheClient;
import com.tp.sp.swapi.swapiclient.clients.cached.SwapiEtagClient;
import com.tp.sp.swapi.swapiclient.clients.http.FilmsHttpClient;
import com.tp.sp.swapi.swapiclient.clients.http.PeopleHttpClient;
import com.tp.sp.swapi.swapiclient.clients.http.PlanetsHttpClient;
import com.tp.sp.swapi.swapiclient.page.FindAllPages;
import lombok.val;

@SuppressWarnings("checkstyle:JavadocTagContinuationIndentation")
public class SwapiClientsFactory {

  private final SwapiClientProperties swapiClientProperties;
  private final SwapiGetMethodClient getMethodClient;
  private final SwapiEtagClient swapiEtagClient;

  /**
   * Creates new factory instance based on {@link SwapiClientProperties} and {@link
   * SwapiResponseMapper} used for JSON response mappings.
   *
   * <p>Creates {@link SwapiGetMethodClient} used by specific clients implementations to handle
   * HTTP GET requests.
   *
   * @param httpClientFactory factory creating {@link reactor.netty.http.client.HttpClient}
   * instance.
   * @param swapiClientProperties swapi client properties.
   * @param responseMapper response mapper.
   */
  public SwapiClientsFactory(HttpClientFactory httpClientFactory,
      SwapiClientProperties swapiClientProperties,
      SwapiResponseMapper responseMapper) {
    this.swapiClientProperties = swapiClientProperties;
    this.getMethodClient = new SwapiGetMethodClient(httpClientFactory.create(),
        responseMapper);
    this.swapiEtagClient = new SwapiEtagClient(httpClientFactory.create());
  }

  /**
   * Creates PeopleClient implementation.
   *
   * @return people client.
   */
  public PeopleClient createPeopleClient() {
    val peopleClient = new PeopleHttpClient(new FindAllPages<>(getMethodClient), getMethodClient,
        swapiClientProperties.getPeopleUri());
    return new PeopleCacheClient(swapiClientProperties.getPeopleUri(), peopleClient,
        swapiEtagClient);
  }

  /**
   * Creates PlanetsClient implementation.
   *
   * @return planets client.
   */
  public PlanetsClient createPlanetsClient() {
    val planetsClient = new PlanetsHttpClient(new FindAllPages<>(getMethodClient), getMethodClient,
        swapiClientProperties.getPlanetsUri());
    return new PlanetsCacheClient(swapiClientProperties.getPlanetsUri(), planetsClient,
        swapiEtagClient);
  }

  /**
   * Creates FilmsClient implementation.
   *
   * @return films client.
   */
  public FilmsClient createFilmsClient() {
    val filmsClient = new FilmsHttpClient(new FindAllPages<>(getMethodClient), getMethodClient,
        swapiClientProperties.getFilmsUri());
    return new FilmsCacheClient(swapiClientProperties.getFilmsUri(), filmsClient, swapiEtagClient);
  }
}
