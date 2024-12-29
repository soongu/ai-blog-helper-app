package com.example.bloghelper.controller;

import com.example.bloghelper.dto.PostCreateRequest;
import com.example.bloghelper.dto.PostResponse;
import com.example.bloghelper.dto.PostUpdateRequest;
import com.example.bloghelper.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * PostController는 게시글과 관련된 HTTP 요청을 처리하는 컨트롤러 클래스입니다.
 * 요청을 받아서 PostService를 통해 비즈니스 로직을 수행하고, 그 결과를 ResponseEntity를 통해 클라이언트에게 반환합니다.
 */
@RestController // 해당 클래스가 REST API의 엔드포인트를 제공하는 컨트롤러임을 명시합니다.
@RequestMapping("/api/posts") // '/api/posts' 경로를 시작점으로 하는 요청들을 이 컨트롤러가 처리합니다.
@RequiredArgsConstructor // Lombok 어노테이션으로, final 필드에 대한 생성자를 자동으로 생성합니다.
public class PostController {
    private final PostService postService;
    // PostService 인스턴스를 주입받아 게시글 생성, 조회, 수정, 삭제 등에 대한 비즈니스 로직을 수행합니다.

    /**
     * 게시글 생성을 위한 엔드포인트입니다.
     * POST /api/posts 로 호출되며, 요청 바디로 PostCreateRequest DTO를 받습니다.
     * 유효성 검사(@Valid)를 통해 필수 값(제목, 내용) 검증이 수행됩니다.
     *
     * @param request 생성할 게시글 정보(제목, 내용)를 담고 있는 DTO
     * @return 생성된 게시글 정보를 담은 DTO를 응답 바디로 반환합니다.
     */
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody @Valid PostCreateRequest request) {
        // 생성된 게시글 정보를 PostService를 통해 받아서 200 OK 응답과 함께 반환합니다.
        return ResponseEntity.ok().body(postService.createPost(request));
    }

    /**
     * 특정 게시글을 조회하기 위한 엔드포인트입니다.
     * GET /api/posts/{id} 로 호출되며, 경로 변수로 게시글의 id를 받습니다.
     *
     * @param id 조회할 게시글의 식별자(ID)
     * @return 해당 게시글 정보를 담은 DTO를 응답 바디로 반환합니다.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        // 해당 ID의 게시글을 조회하여 200 OK 응답과 함께 반환합니다.
        return ResponseEntity.ok().body(postService.getPost(id));
    }

    /**
     * 모든 게시글을 조회하기 위한 엔드포인트입니다.
     * GET /api/posts 로 호출되며, 최신 순으로 정렬된 게시글 리스트를 반환합니다.
     *
     * @return 모든 게시글 정보 리스트를 응답 바디로 반환합니다.
     */
    @GetMapping
    public ResponseEntity<?> getAllPosts() {
        // 모든 게시글 리스트를 조회하여 200 OK 응답과 함께 반환합니다.
        return ResponseEntity.ok().body(postService.getAllPosts());
    }

    /**
     * 특정 게시글을 수정하기 위한 엔드포인트입니다.
     * PUT /api/posts/{id} 로 호출되며, 요청 바디로 PostUpdateRequest DTO를 받습니다.
     * 유효성 검사(@Valid)를 통해 필수 값(제목, 내용) 검증이 수행됩니다.
     *
     * @param id      수정할 게시글의 식별자(ID)
     * @param request 수정할 제목과 내용을 담은 DTO
     * @return 수정된 게시글 정보를 담은 DTO를 응답 바디로 반환합니다.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostUpdateRequest request
    ) {
        // 해당 ID의 게시글을 수정한 후 200 OK 응답과 함께 반환합니다.
        return ResponseEntity.ok().body(postService.updatePost(id, request));
    }

    /**
     * 특정 게시글을 삭제하기 위한 엔드포인트입니다.
     * DELETE /api/posts/{id} 로 호출되며, 경로 변수로 게시글의 id를 받습니다.
     *
     * @param id 삭제할 게시글의 식별자(ID)
     * @return 삭제 성공 시, 204 No Content 응답을 반환합니다.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        // 해당 ID의 게시글을 삭제한 후 204 No Content 응답을 반환합니다.
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
}
