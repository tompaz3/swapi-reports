# Swapi Client

This project contains Java client implementation for [swapi](1) REST API.

Implementation uses [Project Reactor](2) and Project Reactor's [HttpClient](3) for accessing
swapi REST API.

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