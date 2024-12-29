package com.study.springprj.chap07.service;

import com.study.springprj.chap07.dto.CreatePostRequest;
import com.study.springprj.chap07.dto.PostResponseDTO;
import com.study.springprj.chap07.exception.ClientException;
import com.study.springprj.chap07.exception.ServerException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service("postService2")
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final WebClient webClient;

    // POST 요청
    public Mono<PostResponseDTO> createPost(CreatePostRequest requestValue) {
        return webClient
                .post()
                .uri("/posts")
                .bodyValue(requestValue)
                .retrieve()
                // 400번대 에러가 발생했을 경우
                .onStatus(HttpStatusCode::is4xxClientError, response -> {
                    if (response.statusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new ClientException("게시물 페이지를 찾을 수 없습니다!"));
                    }
                    return Mono.error(new ClientException("클라이언트 오류입니다." + response.statusCode()));
                })
                .onStatus(HttpStatusCode::is5xxServerError, response -> Mono.error(new ServerException("서버 오류입니다.")))

                .bodyToMono(PostResponseDTO.class)
                .doOnSuccess(post -> log.info("Created success! - {}", post))
                .doOnError(err -> log.error("Error Creating post! - {}", err.getMessage()))
                ;
    }
}
