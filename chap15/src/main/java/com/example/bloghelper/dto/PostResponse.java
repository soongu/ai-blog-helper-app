package com.example.bloghelper.dto;

import com.example.bloghelper.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResponse {

    // 응답 DTO의 필드: 엔티티(Post)의 식별자, 제목, 내용, 생성일, 수정일을 담습니다.
    private final Long id;
    private final String title;
    private final String content;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Post 엔티티를 기반으로 PostResponse DTO를 생성하는 생성자입니다.
     * 이 생성자를 통해 엔티티의 내부 표현을 외부에 노출 가능한 형태의 DTO로 변환합니다.
     *
     * @param post Post 엔티티 객체
     */
    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
