# SWAPI Reports

This application exposes REST API to generate reports based on data found using 
[https://swapi.co](1) REST API.

Application is created using Java language in OpenJDK 11 version.

## How to use

Application's OpenAPI documentation is available after having it started 
on `/swagger-ui.html` endpoint.

Application exposes REST API for **Report** resorce. There have been 2 versions of report 
generation and storing strategy.

Report is created based on given criteria:
* `query_criteria_character_phrase` - some part of the character's name
* `query_criteria_planet_name` - some part of planet's name, which also needs 
to be character's home world.

### Single Report Strategy

Methods for this strategy are exposed on `/report` endpoint.

Here, every criteria and report_id result in generating single **Report** instance.

First matching Character - Planet pair is used and then, first film found 
for the given character.

### Multiple Report Strategy

Methods for this strategy are exposed on `/v2/report` endpoint.

Here, every criteria and report_id result in generating multiple **Report** instances.

Firstly, all matching Character - Planet pairs are searched.
Then, every character's movie is gathered.
Such data result in creation of **Report** instances flat table, containing multiple 
Character - Planet - Film for single report_id. 

## Build tool

Application build and dependency management is handled by Maven. 
The project contains [Takari Maven Wrapper](2) for convenience.

### Maven modules
Project is separated into a few Maven modules.

#### Main
Parent module, used mainly as starter point for other modules. This parent module has basic
plugin configurations, manages dependencies' versions.

Main module's parent is `spring-boot-starter-parent` module, so every submodule inherits basic
Spring Boot configurations, as well.

### checkstyle
This module is used only for storing and applying `checkstyle.xml` file with Checkstyle rules.

### swapi-client
This module contains an HTTP client for [https://swapi.co](1) REST API.

HTTP client is based on [Project Reactor's](3) `HttpClient` coming from [Reactor Netty](4) project.

Implemented client uses only 3 resources:
 * [https://swapi.co/api/people/](https://swapi.co/api/people/)
 * [https://swapi.co/api/films/](https://swapi.co/api/films/)
 * [https://swapi.co/api/planets/](https://swapi.co/api/planets/)

### domain
This module contains some core domain objects (such as `Report`) and basic logic for retrieving 
data in in the specified manner.

Most crucial parts of this module are actions (e.g. `GetAction` - which gets generated reports from
some repository) and generators (e.g. `GenerateAllPersonPlanetPairFilmsReport` - which generates
`Report` objects according to the given query criteria).

### app
Main application implementation.
Exposes REST API for report resources (`/report`) using Spring Web project.
This module strongly relies on Spring Framework DI and IoC, not implementing the actual 
Spring Application, though.

Implements persistence layer using Spring Data JPA. In memory H2 database is used by default,
but also contains some basic configuration to use PostgreSQL DB.

Database scripts are managed using [Liquibase](5).

Creates implementations for ports specified in `domain` module (e.g. `FindAllFilms` - 
using `FilmsClient` from `swapi-client` module).

This app also exposes Swagger-UI (available at `/swagger-ui.html`) with some basic 
Open API 3.0 documentation.

This module contains some integration tests, where application connects to the real 
[https://swapi.co](1) endpoints. Tests are performed running application on random port
and verifying application's behaviour using [RestAssured](6) testing framework.

To run integration tests, please use `integration-tests` maven profile, e.g.

```
./mvnw clean install -P integration-tests
```

### web-app
This module is used for WAR application packaging - creates executable WAR.
It only imports the **app** module and creates base application entry point, 
which is Spring Boot Application.

To build web-app, use `build-war` Maven profile.


[1]: https://swapi.co
[2]: https://github.com/takari/maven-wrapper
[3]: https://projectreactor.io
[4]: https://projectreactor.io/docs/netty/release/reference/docs/index.html
[5]: https://www.liquibase.org/
[6]: RestAssured