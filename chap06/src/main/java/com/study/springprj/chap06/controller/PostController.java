package com.study.springprj.chap06.controller;

import com.study.springprj.chap06.PostResponseDTO;
import com.study.springprj.chap06.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public Mono<ResponseEntity<?>> getPost(@PathVariable Long id) {
        Mono<PostResponseDTO> post = postService.getPost(id);
        return post
                .map(p -> ResponseEntity.ok().body(p));
    }

    @GetMapping
    public Mono<ResponseEntity<?>> getAllPosts() {
        Flux<PostResponseDTO> allPosts = postService.getAllPosts();
        return allPosts
                .collectList()
                .map(p -> ResponseEntity.ok().body(p));
    }
}
