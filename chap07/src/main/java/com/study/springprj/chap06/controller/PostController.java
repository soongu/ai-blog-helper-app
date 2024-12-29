package com.study.springprj.chap06.controller;

import com.study.springprj.chap06.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v6/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/{id}")
    public Mono<Object> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @GetMapping
    public Flux<Object> getAllPosts() {
        return postService.getAllPosts();
    }
}
