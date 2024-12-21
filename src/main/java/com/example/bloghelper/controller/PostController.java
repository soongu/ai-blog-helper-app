package com.example.bloghelper.controller;

import com.example.bloghelper.dto.PostCreateRequest;
import com.example.bloghelper.dto.PostResponse;
import com.example.bloghelper.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @PostMapping("/drafts")
    public Mono<ResponseEntity<PostResponse>> createDraft(@RequestBody @Valid PostCreateRequest request) {
        return postService.createPostDraft(request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}