# TODO-ES

## Description

This is a pet project for playing with Spring and Event Sourcing

## Quick Start

```
$ docker-compose up -d
```

Once started, open up your browser on http://localhost:8080


## Todos
- [x] Postgres backed event store implementation
- [ ] Store (and retrieve) events as serialized Java Object (see https://www.baeldung.com/java-serialization)
- [ ] Cache read models in separate table using an `@Aysnc @EventListener(ApplicationReadyEvent.class) @Component`
- [ ] Run projector using Spring Batch
