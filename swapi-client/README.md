# Swapi Client

This project contains Java client implementations for [swapi](1) REST API resources. 

Implementation uses [Project Reactor](2) and Project Reactor's [HttpClient](3) for accessing
swapi REST API.

Clients are compatible with [swapi](1) as of 2019-11-07.

## Usage

Project exposes 3 main clients for [https://swapi.co](1) resources.

### FilmsClient

Client retrieves data from [https://swapi.co/api/films/](https://swapi.co/api/films/) resource.
`FilmsClient` has only one method.

`Mono<Films> findAll()` returns all films from the films resource.
Executes GET call directly to [https://swapi.co/api/films/](https://swapi.co/api/films/) URI.

### PeopleClient

Client retrieves data from [https://swapi.co/api/people/](https://swapi.co/api/people/) resource.
`PeopleClient` has only one method.

`Mono<People> findByName(String name)` returns all people whose
name match the given name query phrase. 
Executes GET call to the following URI: 
[https://swapi.co/api/people/?search=name](https://swapi.co/api/people/?search=name) 

### PlanetsClient

Client retrieves data from [https://swapi.co/api/planets/](https://swapi.co/api/planets/) resource.
`PlanetsClient` has only one method.

`Mono<Planets> findByName(String name)` returns all people whose
name match the given name query phrase. \
Executes GET call to the following URI: 
[https://swapi.co/api/planets/?search=name](https://swapi.co/api/planets/?search=name)

## Responses

All clients' responses were generated using [maven-jsonschema2pojo-plugin](4) and schemas retrieved
using [https://swapi.co](1).

Unnecessary fields from responses are ignored.

## Dependencies
Projects contains the following dependencies:

| dependency | description |
| ---------- | ----------- |
| jackson-databind | Helps with JSON to Java POJO mappings | 
| lombok | Used for boilerplate code generation (getters, setters, builders) using annotation processors |
| reactor-core | Applies reactive manner of the client |
| reactor-netty | Used for accessing swapi REST API in reactive manner |
| Vavr | Contains a few functional paradigm Java implementations, e.g. Try or Lazy monads |
| commons-lang3 | Provides utility methods, e.g. for string operations or collection operations |
| junit-jupiter | Java testing library |
| AssertJ | Java fluent Assertions library |

[1]: https://swapi.co/
[2]: https://projectreactor.io/
[3]: https://projectreactor.io/docs/netty/release/reference/index.html#http-client
[4]: https://joelittlejohn.github.io/jsonschema2pojo/site/1.0.1/project-info.html