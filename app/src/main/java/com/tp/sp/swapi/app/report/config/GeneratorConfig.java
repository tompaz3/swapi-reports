package com.tp.sp.swapi.app.report.config;

import com.tp.sp.swapi.app.report.find.FindFilmsByIdsSwapi;
import com.tp.sp.swapi.app.report.find.FindPeopleByNameSwapi;
import com.tp.sp.swapi.app.report.find.FindPlanetsByNameSwapi;
import com.tp.sp.swapi.app.report.find.mapping.FilmMapper;
import com.tp.sp.swapi.app.report.find.mapping.PersonMapper;
import com.tp.sp.swapi.app.report.find.mapping.PlanetMapper;
import com.tp.sp.swapi.domain.GenerateReportFactory;
import com.tp.sp.swapi.domain.generate.GenerateReport;
import com.tp.sp.swapi.domain.model.Report;
import com.tp.sp.swapi.domain.port.FindFilmsByIds;
import com.tp.sp.swapi.domain.port.FindPeopleByName;
import com.tp.sp.swapi.domain.port.FindPlanetsByName;
import com.tp.sp.swapi.swapiclient.SwapiClientsFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DependsOn("swapiClientsFactory")
@Configuration
public class GeneratorConfig {

  @Bean
  public FindPeopleByName findPeopleByName(SwapiClientsFactory swapiClientsFactory) {
    return new FindPeopleByNameSwapi(swapiClientsFactory.createPeopleClient(), new PersonMapper());
  }

  @Bean
  public FindPlanetsByName findPlanetsByName(SwapiClientsFactory swapiClientsFactory) {
    return new FindPlanetsByNameSwapi(swapiClientsFactory.createPlanetsClient(),
        new PlanetMapper());
  }

  @Bean
  public FindFilmsByIds findAllFilms(SwapiClientsFactory swapiClientsFactory) {
    return new FindFilmsByIdsSwapi(swapiClientsFactory.createFilmsClient(), new FilmMapper());
  }

  @Bean
  public GenerateReportFactory generateReportFactory(FindPeopleByName findPeopleByName,
      FindPlanetsByName findPlanetsByName, FindFilmsByIds findFilmsByIds) {
    return new GenerateReportFactory(findPeopleByName, findPlanetsByName, findFilmsByIds);
  }

  @Bean
  public GenerateReport<Mono<Report>> singleReportGenerator(
      GenerateReportFactory generateReportFactory) {
    return generateReportFactory.personPlanetFilm();
  }

  @Bean
  public GenerateReport<Flux<Report>> multipleReportsGenerator(
      GenerateReportFactory generateReportFactory) {
    return generateReportFactory.allPeoplePlanetPairsAllFilms();
  }
}
