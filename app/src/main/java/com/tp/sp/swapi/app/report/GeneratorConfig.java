package com.tp.sp.swapi.app.report;

import com.tp.sp.swapi.app.report.find.FindAllFilmsSwapi;
import com.tp.sp.swapi.app.report.find.FindPeopleByNameSwapi;
import com.tp.sp.swapi.app.report.find.FindPlanetsByNameSwapi;
import com.tp.sp.swapi.app.report.find.mapping.FilmMapper;
import com.tp.sp.swapi.app.report.find.mapping.PersonMapper;
import com.tp.sp.swapi.app.report.find.mapping.PlanetMapper;
import com.tp.sp.swapi.domain.FindAllFilms;
import com.tp.sp.swapi.domain.FindPeopleByName;
import com.tp.sp.swapi.domain.FindPlanetsByName;
import com.tp.sp.swapi.domain.GenerateReportFactory;
import com.tp.sp.swapi.domain.generate.GenerateReport;
import com.tp.sp.swapi.domain.model.Report;
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
  public FindAllFilms findAllFilms(SwapiClientsFactory swapiClientsFactory) {
    return new FindAllFilmsSwapi(swapiClientsFactory.createFilmsClient(), new FilmMapper());
  }

  @Bean
  public GenerateReportFactory generateReportFactory(FindPeopleByName findPeopleByName,
      FindPlanetsByName findPlanetsByName, FindAllFilms findAllFilms) {
    return new GenerateReportFactory(findPeopleByName, findPlanetsByName, findAllFilms);
  }

  @Bean(name = "generateReportV1")
  public GenerateReport<Mono<Report>> singleReportGenerator(
      GenerateReportFactory generateReportFactory) {
    return generateReportFactory.personPlanetFilm();
  }

  @Bean(name = "generateReportV2")
  public GenerateReport<Flux<Report>> personPlanetPairFilmsGenerator(
      GenerateReportFactory generateReportFactory) {
    return generateReportFactory.personPlanetPairAllFilms();
  }

  @Bean(name = "generateReportV3")
  public GenerateReport<Flux<Report>> personPlanetPairsFilmsGenerator(
      GenerateReportFactory generateReportFactory) {
    return generateReportFactory.allPeoplePlanetPairsAllFilms();
  }
}
