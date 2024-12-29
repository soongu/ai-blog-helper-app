package com.study.springprj.chap05.flux;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import lombok.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Flux와 관련된 기본적인 예제들을 담은 컨트롤러입니다.
 * Flux는 0..N개의 데이터를 처리하는 Publisher입니다.
 */
@RestController
@RequestMapping("/api/v5/flux")
public class FluxExample {

    /**
     * 기본적인 Flux 생성과 변환 예제입니다.
     * 여러 숫자를 처리하는 방법을 보여줍니다.
     */
    @GetMapping("/simple")
    public Flux<Integer> simpleFluxExample() {
        // just()로 여러 개의 데이터를 발행하는 Flux를 생성합니다
        return Flux.just(1, 2, 3, 4, 5)
                // 각 숫자를 2배로 변환
                .map(number -> number * 2);
    }

    /**
     * 리스트 데이터를 Flux로 변환하는 예제입니다.
     * fromIterable을 사용한 Flux 생성 방법을 보여줍니다.
     */
    @GetMapping("/list")
    public Mono<List<String>> listExample() {
        List<String> items = List.of("Apple", "Banana", "Orange");
        // fromIterable()로 리스트를 Flux로 변환합니다
        return Flux.fromIterable(items)
                .map(String::toUpperCase)
                .collectList();
    }

    /**
     * 실제 비즈니스 로직과 유사한 상품 목록 처리 예제입니다.
     * filter와 map을 조합하여 사용하는 방법을 보여줍니다.
     */
    @Data
    @AllArgsConstructor
    class Product {
        private String name;
        private double price;
    }

    @GetMapping("/products")
    public Flux<Product> getProducts() {
        // 샘플 상품 데이터 생성
        List<Product> products = List.of(
                new Product("Laptop", 1200.00),
                new Product("Phone", 800.00),
                new Product("Tablet", 500.00)
        );

        return Flux.fromIterable(products)
                // 700달러 이상의 상품만 필터링
                .filter(product -> product.getPrice() > 700.00)
                // 상품명을 대문자로 변환
                .map(product -> {
                    product.setName(product.getName().toUpperCase());
                    return product;
                });
    }
}