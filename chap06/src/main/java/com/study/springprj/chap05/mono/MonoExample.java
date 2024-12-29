package com.study.springprj.chap05.mono;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v5/mono")
public class MonoExample {

    @GetMapping("/simple")
    public Mono<String> simple() {
        Mono<String> mono = Mono.just("Hello, Reactive world!");
        return mono;
    }

    @GetMapping("/transform")
    public Mono<String> transform() {
        return Mono.just("hello")
                .map(String::toUpperCase)
                .map(str -> str + " WORLD!")
                ;
    }

}
