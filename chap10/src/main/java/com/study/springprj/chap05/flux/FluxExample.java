package com.study.springprj.chap05.flux;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/v5/flux")
public class FluxExample {

    @GetMapping("/simple")
    public Flux<Integer> simple() {
        return Flux.just(1, 2, 3, 4, 5)
                .map(n -> n * 3)
                ;
    }

    @GetMapping("/list")
    public Mono<List<String>> list() {
        List<String> fruits = List.of("Apple", "Banana", "Orange");

        return Flux.fromIterable(fruits)
                .map(String::toUpperCase)
                .collectList()
                ;
    }

    @AllArgsConstructor
    @Setter
    @Getter @ToString
    private static class Product {
        private String name;
        private int price;
    }

    @GetMapping("/products")
    public Flux<Product> productFlux() {
        // 샘플 상품
        List<Product> products = List.of(
                new Product("노트북", 1000000),
                new Product("휴대폰", 1300000),
                new Product("태블릿", 1700000)
        );

        return Flux.fromIterable(products)
                .filter(p -> p.getPrice() > 1000000)
                .map(p -> {
                    p.setName(p.getName() + "!!!");
                    return p;
                })
                ;
    }


}
