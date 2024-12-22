package com.example.bloghelper.controller;

import com.example.bloghelper.dto.*;
import com.example.bloghelper.service.PostImproveService;
import com.example.bloghelper.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@Tag(name = "Posts", description = "게시글 관련 API")

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {
    private final PostService postService;
    private final PostImproveService postImproveService;
    // 임시로 추가
//    private final MemberRepository memberRepository;


    @Operation(summary = "블로그 초안 게시글 작성", description = "새로운 블로그 초안을 작성합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청")
    })

    @PostMapping("/drafts")
    public ResponseEntity<PostResponse> createDraft(
            @Parameter(hidden = true) @AuthenticationPrincipal String email,
            @Parameter(description = "게시글 정보", required = true) @RequestBody @Valid PostCreateRequest request
    ) {
        log.info("authenticated email: {}", email);

        // 임시로 첫번째 회원을 찾아서 사용
//        Member member = memberRepository.findAll()
//                .stream()
//                .findFirst()
//                .orElseThrow();


        return ResponseEntity
                .ok()
                .body(postService.createPostDraft(request, email)
                        .block())
                ;
    }

    @Operation(summary = "블로그 포스트 개선", description = "ChatGPT를 사용하여 블로그 포스트를 개선합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 개선 성공"),
            @ApiResponse(responseCode = "404", description = "게시글을 찾을 수 없음"),
            @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자")
    })
    @PostMapping("/{postId}/improve")
    public Mono<ResponseEntity<PostImproveResponse>> improvePost(
            @Parameter(description = "게시글 ID") @PathVariable Long postId,
            @Parameter(description = "개선 요청 정보") @RequestBody @Valid PostImproveRequest request
    ) {
        return postImproveService.improvePost(postId, request)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }


    @Operation(summary = "블로그 포스트 개선 이력 조회", description = "블로그 포스트의 개선 이력을 조회합니다.")
    @SecurityRequirement(name = "bearerAuth")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "조회 성공"),
            @ApiResponse(responseCode = "403", description = "인증되지 않은 사용자")
    })

    @GetMapping("/{postId}/histories")
    public ResponseEntity<List<PostHistoryResponse>> getPostHistories(
            @Parameter(description = "게시글 ID") @PathVariable Long postId) {
        return ResponseEntity.ok()
                .body(postImproveService.getPostHistories(postId));
    }
}