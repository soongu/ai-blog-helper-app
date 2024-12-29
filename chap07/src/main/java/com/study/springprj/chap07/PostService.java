package com.study.springprj.chap07;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final WebClient webClient;

    public Mono<PostDTO> getPostWithErrorHandling(Long id) {
        return webClient
                .get()
                .uri("/posts/{id}", id)
                .retrieve()
                // 4xx 에러 처리
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new Exception("Post not found with id: " + id));
                    }
                    return Mono.error(new Exception("Client error: " + response.statusCode()));
                })
                // 5xx 에러 처리
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new Exception("Server error: " + response.statusCode())))
                .bodyToMono(PostDTO.class)
                // 타임아웃 설정
                .timeout(Duration.ofSeconds(5))
                // 재시도 로직
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(1))
                        .doBeforeRetry(retrySignal ->
                                log.info("Retrying... attempt: {}", retrySignal.totalRetries())))
                .doOnError(error -> log.error("Final error: {}", error.getMessage()));
    }

    public Mono<PostDTO> getPostWithHeaders(Long id, String authToken) {
        return webClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/posts/{id}")
                        .queryParam("_expand", "comments")
                        .build(id))
                .header("Authorization", "Bearer " + authToken)
                .header("Custom-Header", "custom-value")
                .retrieve()
                .bodyToMono(PostDTO.class);
    }
}