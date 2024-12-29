package com.study.springprj.chap06.service;

import com.study.springprj.chap06.PostResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private final WebClient webClient;

    public Mono<PostResponseDTO> getPost(Long id) {
        log.info("Fetching post with id: {}", id);

        return webClient
                .get()
                .uri("/posts/{id}", id)
                .retrieve()
                .bodyToMono(PostResponseDTO.class)
                ;

    }

    public Flux<PostResponseDTO> getAllPosts() {
        return webClient
                .get()
                .uri("/posts")
                .retrieve()
                .bodyToFlux(PostResponseDTO.class);
    }
}
