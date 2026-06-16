package com.example.backend.controller;

import com.example.backend.dto.UserDto;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserDto> streamUsers() {
        List<UserDto> users = Arrays.asList(
            new UserDto(1L, "Alice Johnson", "alice@example.com"),
            new UserDto(2L, "Bob Smith", "bob@example.com"),
            new UserDto(3L, "Charlie Brown", "charlie@example.com"),
            new UserDto(4L, "Diana Prince", "diana@example.com"),
            new UserDto(5L, "Ethan Hunt", "ethan@example.com"),
            new UserDto(6L, "Fiona Clark", "fiona@example.com")
        );

        return Flux.fromIterable(users).delayElements(Duration.ofSeconds(1));
    }
}
