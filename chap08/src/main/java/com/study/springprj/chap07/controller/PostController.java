package com.study.springprj.chap07.controller;

import com.study.springprj.chap07.dto.CreatePostRequest;
import com.study.springprj.chap07.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController("post2")
@RequestMapping("/api/v7/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public Mono<ResponseEntity<?>> posts(@RequestBody CreatePostRequest dto) {
        return postService.createPost(dto)
                .map(post -> ResponseEntity.ok().body(post));
    }
}
