package com.tp.sp.swapi.domain.generate;

import com.tp.sp.swapi.domain.model.Film;
import com.tp.sp.swapi.domain.model.Person;
import com.tp.sp.swapi.domain.model.Planet;
import com.tp.sp.swapi.domain.model.QueryCriteria;
import com.tp.sp.swapi.domain.model.Report;
import com.tp.sp.swapi.domain.port.FindFilmsByIds;
import com.tp.sp.swapi.domain.port.FindPersonWithFilmAndPlanetByCriteria;
import com.tp.sp.swapi.domain.port.PersonPlanet;
import io.vavr.Tuple3;
import io.vavr.control.Option;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.val;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class GenerateAllPersonPlanetPairFilmsReport implements GenerateReport<Flux<Report>> {

  private final FindPersonWithFilmAndPlanetByCriteria findPersonWithFilmAndPlanetByCriteria;
  private final FindFilmsByIds findFilmsByIds;
  private final GenerateReportFromTupleMapper generateReportFromTupleMapper;

  /**
   * Generates report base ond given query criteria and report id.
   *
   * <p>Query criteria consist of {@code planet name} and {@code character phrase}.
   * Character must have his home world from the given planet.
   *
   * <p>If no planet - character pair is
   * found, report is not generated.
   *
   * <p>If more than one planet - character pair matches the query, {@link Report} instances are
   * generated for every pair.
   *
   * <p>If person has more than one film, all films found are mapped and multiple {@link Report}
   * instances are generated.
   *
   * @param reportId report id.
   * @param queryCriteria query criteria ({@code planet name} and {@code character phrase}).
   * @return generated {@link Report} instances or empty Flux.
   */
  @Override
  public Flux<Report> generateReport(int reportId, QueryCriteria queryCriteria) {
    return findPersonWithFilmAndPlanetByCriteria.findByCriteria(queryCriteria)
        .collectList()
        .flatMapMany(this::findFilms)
        .map(t3 -> generateReportFromTupleMapper.toReport(reportId, queryCriteria, t3));
  }

  private Flux<Tuple3<Person, Planet, Film>> findFilms(List<PersonPlanet> peoplePlanets) {
    val filmIds = filmIdsFromPeoplePlanets(peoplePlanets);
    return findFilmsByIds.findAllByIds(filmIds)
        .flatMap(f -> assignFilm(peoplePlanets, f));
  }

  private Flux<Tuple3<Person, Planet, Film>> assignFilm(List<PersonPlanet> peoplePlanets,
      Film film) {
    return Flux.fromStream(
        peoplePlanets.stream()
            .map(p -> p.joinFilmAndRemove(film))
            .filter(Option::isDefined)
            .map(Option::get)
    );
  }

  private Set<Integer> filmIdsFromPeoplePlanets(List<PersonPlanet> peoplePlanets) {
    return peoplePlanets.stream()
        .map(PersonPlanet::getFilmIds)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }
}
