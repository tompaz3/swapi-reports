# SWAPI Reports

This application exposes REST API to generate reports based on data found using 
[https://swapi.co](1) REST API.

Application is created using Java language in OpenJDK 11 version.

## Foreword

Chosen implementation, especially Project Reactor dependency, isn't the best fit 
for such application.
If such application was to be created ever again, a different approach might work out way better.

The `domain` module has too much dependency on Project Reactor publishers and should probably
have at least some abstraction over publishers (or even some result wrappers) to be truly
independent from external libraries.

The application may be regarded as a playground, PoC for trying to use some 
patterns of [Hexagonal Architecture](7) design and Project Reactor.

[https://swapi.co](1) isn't elastic enough and doesn't allow easily to access joined data 
(e.g. to access all species' details for planets, which name contains letter "i", 
one has to execute multiple queries).

Project Reactor use for such simple application is a bit of an overkill, since all data
need to be accessed in a blocking manner, anyway.


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

## Project dependencies

Project uses the following dependencies:

* [Spring Boot Starter Web](8) - auto configurations for Spring Web Application
* [Spring Boot Starter Data Jpa](9) - auto configurations for Spring Data JPA
    * [Hibernate](10)
    * [Liquibase](5)
* [Spring Boot Starter Test](11) - auto configurations for Spring Applications testing
* [SpringDoc OpenAPI UI](12) - library and auto configuration for Spring Web, adding `/swagger-ui.html`
endpoint and helping document REST APIs using OpenAPI 3.0 standard
* [Lombok](13) - auto generation of Java bolierplate code (such as getters, setters, builders)
* [Project Reactor](3) - Java reactive library, including Reactive Netty
* [FasterXML Jackson](14) - Java library for JSON manipulation
* [Vavr.io](15) - java library porting some functional paradigm design to Java 
(e.g. Option, Try, Either monads or immutable collections)
* [Apache Commons-Lang3](16) - library containing common utility classes (e.g. `StringUtils`)
* [Apache Commons-Collections4](17) - library containing common collection utilities 
(here, used for `LRUMap` implementation).
* [H2](18) - in memory Java database (used by default)
* [Postgresql](19) - JDBC for PostgreSQL (not used by default, requires using `postgres` Spring profile)
* [JUnit Jupiter](20) - Java library for implementing and running tests
* [AssertJ](21) - library for fluent assertions (used in tests)
* [Rest Assured](6) - library for fluent REST APIs tests

Project uses the following maven plugins:
* Maven Compiler Plugin - for Java code compilation, 
(includes Lombok Annotation Processor configured)
* Maven Surefire Plugin - for running tests during maven application build lifecycle
* Maven Checkstyle Plugin - for running Checkstyle verifications during 
maven application build lifecycle
* JsonSchema2Pojo - plugin for generating Java sources based on JsonSchema JSON files.
* Spring Boot Maven Plugin - for building executable JAR/WAR file of the entire application.
* Maven Antrun Plugin - just to copy the output JAR/WAR file to the main project location.




[1]: https://swapi.co
[2]: https://github.com/takari/maven-wrapper
[3]: https://projectreactor.io
[4]: https://projectreactor.io/docs/netty/release/reference/docs/index.html
[5]: https://www.liquibase.org/
[6]: http://rest-assured.io/
[7]: https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)
[8]: https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project/spring-boot-starters/spring-boot-starter-web
[9]: https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project/spring-boot-starters/spring-boot-starter-data-jpa
[10]: https://hibernate.org/
[11]: https://github.com/spring-projects/spring-boot/tree/master/spring-boot-project/spring-boot-starters/spring-boot-starter-test
[12]: https://github.com/springdoc/springdoc-openapi
[13]: https://projectlombok.org/
[14]: https://github.com/FasterXML/jackson-databind
[15]: https://www.vavr.io/
[16]: https://commons.apache.org/proper/commons-lang/
[17]: http://commons.apache.org/proper/commons-collections/
[18]: https://h2database.com/html/main.html
[19]: https://www.postgresql.org/
[20]: https://junit.org/junit5/
[21]: https://assertj.github.io/doc/
