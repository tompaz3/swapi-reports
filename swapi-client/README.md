# Swapi Client

This project contains Java client implementations for [swapi](1) REST API resources. 

Implementation uses [Project Reactor](2) and Project Reactor's [HttpClient](3) for accessing
swapi REST API.

Clients are compatible with [swapi](1) as of 2019-11-07.

## Usage

Project exposes 3 main clients for [https://swapi.co](1) resources.

### FilmsClient

Client retrieves data from [https://swapi.co/api/films/](https://swapi.co/api/films/) resource.

### PeopleClient

Client retrieves data from [https://swapi.co/api/people/](https://swapi.co/api/people/) resource. 

### PlanetsClient

Client retrieves data from [https://swapi.co/api/planets/](https://swapi.co/api/planets/) resource.

---

All the above clients have some simple caching mechanism implemented, 
based on Etag returned by [https://swapi.co](1). Cached values are stored in `LRUMap` from
`org.apache.commons:commons-collections4` library.

Cache could be more intelligent, or be even updated by some scheduled job, but for the time being,
getting results works as follows:
1. check Etag for given resource
1. if Etag is the same as the last stored
    1. for People and Planets - filter cache by given search phrase
        1. appeared before - brilliant, simply retrieve from the cache map
        1. not stored - fetch from [https://swapi.co](1) API, update cache and return 
        (those 2 steps could've been done asynchronously for better performance)
    1. for  Films - filter cache by given ids
        1. if all ids appeared before - return films from the cache map
        1. a few ids haven't appeared before - get missing id films and update cache map 
        (those 2 steps could've been done asynchronously for better performance)
        Some performance test could've been done to determine what's better - fetching 2-3 films 
        by their individual ids or fetching page by page and trying
        to find the appropriate ones. Since there are only 7 movies returned by [https://swapi.co](1)
        as of this day, all films are retrieved regardless of number of missing ids.
        1. all ids missing in cache - fetch films and update cache map 
        (those 2 steps could've been done asynchronously, as well)
1. return values found from cache
        

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