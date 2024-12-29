package com.study.springprj.chap05.mono;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Mono와 관련된 기본적인 예제들을 담은 컨트롤러입니다.
 * Mono는 0 또는 1개의 데이터를 처리하는 Publisher입니다.
 */
@RestController
@RequestMapping("/api/v5/mono")
public class MonoExample {

    /**
     * 가장 기본적인 Mono 생성 예제입니다.
     * Mono.just()를 사용해 단일 데이터를 발행합니다.
     */
    @GetMapping("/simple")
    public Mono<String> simpleMonoExample() {
        // just()는 가장 기본적인 Mono 생성 방법입니다
        return Mono.just("Hello, Reactive World!");
    }

    /**
     * Mono의 데이터 변환(transformation) 예제입니다.
     * map() 연산자를 사용해 데이터를 변환하는 방법을 보여줍니다.
     */
    @GetMapping("/transform")
    public Mono<String> transformExample() {
        return Mono.just("hello")
                // map을 통해 데이터를 대문자로 변환
                .map(str -> str.toUpperCase())
                // 연속적인 map 호출을 통해 데이터를 계속 변환할 수 있습니다
                .map(str -> "Transformed: " + str);
    }

    /**
     * 사용자 데이터를 다루는 실제 활용 예제입니다.
     * filter와 map을 함께 사용하는 방법을 보여줍니다.
     */
    @Data
    @AllArgsConstructor
    class User {
        private String name;
        private int age;
    }

    @GetMapping("/user/{id}")
    public Mono<User> getUser(@PathVariable String id) {
        return Mono.just(new User("John Doe", 30))
                // filter로 조건에 맞는 데이터만 통과시킵니다
                .filter(user -> user.getAge() > 20)
                // map으로 데이터를 변환합니다
                .map(user -> {
                    user.setName(user.getName().toUpperCase());
                    return user;
                });
    }
}


