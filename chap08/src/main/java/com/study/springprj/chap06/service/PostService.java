package com.study.springprj.chap06.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PostService {

    private final WebClient webClient;

//    @Autowired
//    public PostService(WebClient webClient) {
//        this.webClient = webClient;
//    }


    // 게시물 1개를 가져오는 메서드
    public Mono<Object> getPost(Long id) {

        Mono<Object> mono = webClient.get()
                .uri("/posts/{id}", id)
                .retrieve() // 응답 결과를 받는 중간 연산
                .bodyToMono(Object.class);// 응답 결과를 모노로 리턴

        return mono;
    }

    // 전체 게시물을 가져오기
    public Flux<Object> getAllPosts() {
        Flux<Object> flux = webClient
                .get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(Object.class);

        return flux;
    }

}
