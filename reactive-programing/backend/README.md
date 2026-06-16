# Backend - Reactive SSE API

This backend is built with Java 8, Spring Boot 2.7.7, and Maven.

## What it does

- Exposes a reactive endpoint: `GET /api/users/stream`
- Response type is `Flux<UserDto>`
- Produces server-sent events (`text/event-stream`)

## Run

```bash
mvn spring-boot:run
```

The app starts on `http://localhost:8080`.

## Endpoint

- Stream users: `http://localhost:8080/api/users/stream`
