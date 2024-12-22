package com.example.bloghelper.dto;

import com.example.bloghelper.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;


@Schema(description = "회원 정보 응답 DTO")
public record MemberResponse(
        @Schema(description = "회원 ID", example = "1")
        Long id,
        @Schema(description = "이메일", example = "user@example.com")
        String email,
        @Schema(description = "닉네임", example = "블로거")
        String nickname,
        @Schema(description = "계정 생성일시", example = "2024-01-01T12:00:00")
        LocalDateTime createdAt
) {
    public static MemberResponse from(Member member) {
        return new MemberResponse(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getCreatedAt()
        );
    }
}

